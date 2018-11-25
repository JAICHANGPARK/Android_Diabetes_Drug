package  com.dreamwalker.multiselection.callbacks


interface ListInterface<I> {
    fun removeItemAt(position: Int): I
    fun indexOf(item: I): Int
    fun add(item: I): Int
}