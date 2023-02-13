package com.mrmpn.videogamedb.ui.providers

import java.io.File

fun readFileAsTextUsingInputStream(fileName: String) =
    File(fileName).inputStream().readBytes().toString(Charsets.UTF_8)

/**
 * This a workaround, PreviewParameterProvider code apparently runs
 * on a different working directory (at least for me) than the actual project, so the only
 * easy way to get the proper path is hardcoding the actual project root path in
 * WorkingDirectory.basePath (and also not commit that as it's computer-specific)
 *
 */
fun getFileFullPath(filePath: String): String {
    return StringBuilder()
        .append(WorkingDirectory.basePath)
        .append(filePath)
        .toString()
}