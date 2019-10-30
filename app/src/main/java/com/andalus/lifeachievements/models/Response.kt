package com.andalus.lifeachievements.models

import com.apollographql.apollo.api.Operation

class Response<T : Operation.Data>(val errors: List<Error>, val data: T?)