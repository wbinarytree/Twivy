package wbinarytree.github.io.twivy.utils

inline fun <reified T> Any.safeCast(action: T.() -> Unit) {
    if (this is T) {
        this.action()
    }
}

inline fun <reified T> List<T>?.toNonNullList(): List<T> {
    return this ?: emptyList()
}