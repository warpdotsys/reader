// Restored from reader-pro-3.2.14.jar; see docs/PROVENANCE.md.
package io.legado.app.adapters

object ReaderAdapterHelper {

    var readerAdapter: ReaderAdapterInterface = DefaultAdpater()

    fun setAdapter(adapter: ReaderAdapterInterface) {
        readerAdapter = adapter
    }

    fun getAdapter(): ReaderAdapterInterface {
        return readerAdapter
    }
}
