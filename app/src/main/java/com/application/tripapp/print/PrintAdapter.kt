package com.application.tripapp.print

import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentInfo
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.io.FileOutputStream
import java.io.IOException

class PrintAdapter(private val context: Context, private val view: View) : PrintDocumentAdapter() {
    private lateinit var mPdfDocument: PdfDocument

    override fun onLayout(
        oldAttributes: PrintAttributes?,
        newAttributes: PrintAttributes?,
        cancellationSignal: CancellationSignal?,
        callback: LayoutResultCallback?,
        extras: Bundle?
    ) {
        mPdfDocument = PdfDocument()

        if (cancellationSignal?.isCanceled == true) {
            callback?.onLayoutCancelled()
            return
        }

        val pageInfo = PdfDocument.PageInfo.Builder(view.width, view.height, 1).create()
        val page = mPdfDocument.startPage(pageInfo)

        view.draw(page.canvas)
        mPdfDocument.finishPage(page)

        callback?.onLayoutFinished(
            PrintDocumentInfo.Builder("Print Output")
                .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
                .setPageCount(mPdfDocument.pages.size)
                .build(), true
        )
    }

    override fun onWrite(
        pages: Array<out PageRange>?,
        destination: ParcelFileDescriptor?,
        cancellationSignal: CancellationSignal?,
        callback: WriteResultCallback?
    ) {
        try {
            mPdfDocument.writeTo(FileOutputStream(destination?.fileDescriptor))
        } catch (e: IOException) {
            callback?.onWriteFailed(e.message)
            return
        } finally {
            if (mPdfDocument.pages.isNotEmpty()) {
                mPdfDocument.close()
            }
        }

        callback?.onWriteFinished(arrayOf(PageRange.ALL_PAGES))
    }
}

