package org.danilkha;

import org.jetbrains.annotations.Nullable;

abstract public sealed class Result<T> permits Result.Success, Result.Error{
    public static final class Success<T> extends Result<T>{
        private final T data;

        public Success(T data) {
            this.data = data;
        }
        public T getData() {
            return data;
        }
    }
    public static final class Error<T> extends Result<T>{
        private final String message;
        private final Throwable throwable;

        public Error(String message, @Nullable Throwable throwable) {
            this.message = message;
            this.throwable = throwable;
        }

        public Error(String message) {
            this.message = message;
            this.throwable = null;
        }


        public String getMessage() {
            return message;
        }
    }
}
