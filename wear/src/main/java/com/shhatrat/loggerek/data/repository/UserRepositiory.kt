package com.shhatrat.loggerek.data.repository

import com.shhatrat.loggerek.util.base.Preferences


/**
 * Created by szymon on 24/12/17.
 */
class UserRepositiory: Preferences(){

    var name by stringPref()
}