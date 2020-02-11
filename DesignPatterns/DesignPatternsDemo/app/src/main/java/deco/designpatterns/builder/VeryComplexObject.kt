import androidx.annotation.NonNull

internal class VeryComplexObject(val first: String?, val second: String?,
                                 val third: String?, val fourth: String?) {

    class Builder {
/*        As long as you mean static nested classes, in kotlin a nested class is static by default.
        To make it a non-static inner class it needs to have a inner flag.*/

        private var first: String? = null
        private var second: String? = null
        private var third: String? = null
        private var fourth: String? = null

        fun setFirst(@NonNull first: String): Builder {
            this.first = first
            return this
        }

        fun setSecond(@NonNull second: String): Builder {
            this.second = second
            return this
        }

        fun setThird(@NonNull third: String): Builder {
            this.third = third
            return this
        }

        fun setFourth(@NonNull fourth: String): Builder {
            this.fourth = fourth
            return this
        }

        fun build(): VeryComplexObject {
            return VeryComplexObject(first, second, third, fourth)
        }
    }
}