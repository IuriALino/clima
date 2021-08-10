package com.example.clima.data.source.retrofit

@KoinApiExtension
object RetrofitClient : KoinComponent {
    private val loginRepository by inject<LoginRepository>()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.API_HOST_APLIC)
        .client(httpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun httpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()

        loggingInterceptor.level = when (BuildConfig.DEBUG) {
            true -> HttpLoggingInterceptor.Level.BODY
            else -> HttpLoggingInterceptor.Level.NONE
        }

        return OkHttpClient.Builder().apply {
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(45, TimeUnit.SECONDS)
            writeTimeout(45, TimeUnit.SECONDS)
            retryOnConnectionFailure(false)
            addInterceptor(loggingInterceptor)
            addInterceptor(accessTokenInterceptor())
            addInterceptor(InterceptResponse())
        }.build()
    }

    private class InterceptResponse : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            synchronized(this@InterceptResponse) {
                val request = chain.request()
                val response = chain.proceed(request)

                val isUnauthorized = when (response.code) {
                    401 -> true
                    400 -> {
                        try {
                            val responseBodyCopy = response.peekBody(Long.MAX_VALUE)
                            val bodyString = responseBodyCopy.string()
                            val failure = Gson().fromJson(bodyString, DefaultFailure::class.java)
                            failure.message.equals("Credenciais inválidas", true)
                        } catch (e: Exception) {
                            false
                        }
                    }
                    else -> false
                }

                when (isUnauthorized) {
                    true -> loginRepository.forcedLogout()
                }

                return response
            }
        }
    }

    private fun accessTokenInterceptor() = Interceptor { chain ->
        val request = chain.request()
        request.newBuilder()

        chain.proceed(
            chain.request().newBuilder()
                .addHeader("serial", getSerial())
                .addHeader("appName", "aplic_app")
                .addHeader("versionCode", BuildConfig.VERSION_CODE.toString())
                .addHeader("versionName", BuildConfig.VERSION_NAME)
                .addHeader("Accept-Language", "pt-BR")
                .addHeader("Accept", "application/json")
                .addHeader("Connection", "close")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", AuthMiddleware.getToken())
                .build()
        )
    }

    fun loginService(): LoginService = retrofit.create(LoginService::class.java)
    fun accountService(): AccountService = retrofit.create(AccountService::class.java)
    fun securityService(): SecurityService = retrofit.create(SecurityService::class.java)
    fun transactionService(): TransactionService = retrofit.create(TransactionService::class.java)
    fun reportService(): ReportService = retrofit.create(ReportService::class.java)
    fun invoiceService(): InvoiceService = retrofit.create(InvoiceService::class.java)
}