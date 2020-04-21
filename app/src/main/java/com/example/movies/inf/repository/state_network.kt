package com.example.movies.inf.repository
enum class Status {
    RUNNING,
    SUCCESS,
    FAILED

}
class state_network(val status: Status, val msg: String) {

    companion object {

        val LOADED: state_network
        val LOADING: state_network
        val ERROR: state_network
        val ENDOFLIST:  state_network
        init {
            LOADED = state_network(Status.SUCCESS, "Success")

            LOADING = state_network(Status.RUNNING, "Running")

            ERROR = state_network(Status.FAILED, "Something went wrong")
            ENDOFLIST = state_network(Status.FAILED, "You have reached the end")
        }
    }
}