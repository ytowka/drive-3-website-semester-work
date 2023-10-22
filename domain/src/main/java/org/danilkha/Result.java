package org.danilkha;

abstract public class Result {
    static class Success<T> extends Result{
        private final T data;

        Success(T data) {
            this.data = data;
        }
        public T getData() {
            return data;
        }
    }
    static class Error extends Result{
        private final String message;

        Error(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
