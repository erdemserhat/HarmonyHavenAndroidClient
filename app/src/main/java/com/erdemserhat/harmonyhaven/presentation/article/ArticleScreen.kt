package com.erdemserhat.harmonyhaven.presentation.article

import android.annotation.SuppressLint
import android.util.Log
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.erdemserhat.harmonyhaven.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.erdemserhat.harmonyhaven.ui.theme.harmonyHavenWhite
import dev.jeziellago.compose.markdowntext.MarkdownText

data class ArticleUIModel(
    val title: String,
    val content: String,
    val publishDate: String,
    val category: String?,
    val imagePath: String,
    val isLoaded:Boolean

)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ArticleScreen(
   // articleId: Int,
    //viewModel: ArticleViewModel = hiltViewModel()
) {
  //  viewModel.prepareArticle(articleId)

    //val articleScreenState by viewModel.articleState.collectAsState()

   // val articleUIModel = ArticleUIModel(
    //    title = articleScreenState.articleTitle,
     //   content = articleScreenState.articleContent,
     //   publishDate = articleScreenState.publishDate,
      //  category = articleScreenState.category,
      //  imagePath = articleScreenState.imagePath,
     //   isLoaded = articleScreenState.isLoaded
    //)
    ArticleScreenContent()



}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ArticleScreenContent(
  //  articleUIModel: ArticleUIModel
) {

    Scaffold(
        topBar = {
            ArticleToolbar()
        },
        content = {
            ArticleContent()
        }
    )


}





@Composable
fun ArticleToolbar() {
    TopAppBar(
        elevation = 0.dp, // Kenarlık kalınlığını sıfıra ayarlar
        backgroundColor = Color.Transparent,
        contentColor = Color.Transparent,
        title = {  },
        navigationIcon = {
            IconButton(onClick = { /* Geri gitme işlemi */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.return_back_icon),
                    contentDescription = "Geri",
                    modifier = Modifier.size(32.dp)


                )
            }
        },
        actions = {
            // Kaydetme ve Paylaşma düğmeleri
            IconButton(onClick = { /* Kaydetme işlemi */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.play_icon),
                    contentDescription = "Geri",
                    modifier = Modifier.size(24.dp)


                )
            }
            IconButton(onClick = { /* Paylaşma işlemi */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.shareicon),
                    contentDescription = "Geri",
                    modifier = Modifier.size(24.dp)


                )
            }

            IconButton(onClick = { /* Paylaşma işlemi */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.three_dots_icon),
                    contentDescription = "Geri",
                    modifier = Modifier.size(24.dp)


                )
            }
        },
    )
}

@Composable
fun ArticleContent() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        AsyncImage(
            model = "https://img.freepik.com/free-photo/abstract-autumn-beauty-multi-colored-leaf-vein-pattern-generated-by-ai_188544-9871.jpg?t=st=1715796090~exp=1715799690~hmac=28425ab14b9885e337250e319592a26e80f70b31890dd23a6a853f73b6341027&w=1060", // yer tutucu görüntü
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        //HtmlText(article.content)
        MinimalExampleContent()
    }
}
@Preview(showBackground = true)
@Composable
private fun ArticleScreenContentPreview() {
    ArticleScreenContent()

}

@Composable
fun HtmlText(html: String, modifier: Modifier = Modifier) {
    AndroidView(
        modifier = modifier,
        factory = { context -> TextView(context) },
        update = { it.text = HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_COMPACT) }
    )
}

val markdownContent = """  
An h1 header
============

Paragraphs are separated by a blank line.

2nd paragraph. *Italic*, **bold**, and `monospace`. Itemized lists
look like:

  * this one
  * that one
  * the other one

Note that --- not considering the asterisk --- the actual text
content starts at 4-columns in.

> Block quotes are
> written like so.
>
> They can span multiple paragraphs,
> if you like.

Use 3 dashes for an em-dash. Use 2 dashes for ranges (ex., "it's all
in chapters 12--14"). Three dots ... will be converted to an ellipsis.
Unicode is supported. ☺



An h2 header
------------

Here's a numbered list:

 1. first item
 2. second item
 3. third item

Note again how the actual text starts at 4 columns in (4 characters
from the left side). Here's a code sample:

    # Let me re-iterate ...
    for i in 1 .. 10 { do-something(i) }

As you probably guessed, indented 4 spaces. By the way, instead of
indenting the block, you can use delimited blocks, if you like:

~~~
define foobar() {
    print "Welcome to flavor country!";
}
~~~

(which makes copying & pasting easier). You can optionally mark the
delimited block for Pandoc to syntax highlight it:

~~~python
import time
# Quick, count to ten!
for i in range(10):
    # (but not *too* quick)
    time.sleep(0.5)
    print i
~~~



### An h3 header ###

Now a nested list:

 1. First, get these ingredients:

      * carrots
      * celery
      * lentils

 2. Boil some water.

 3. Dump everything in the pot and follow
    this algorithm:

        find wooden spoon
        uncover pot
        stir
        cover pot
        balance wooden spoon precariously on pot handle
        wait 10 minutes
        goto first step (or shut off burner when done)

    Do not bump wooden spoon or it will fall.

Notice again how text always lines up on 4-space indents (including
that last line which continues item 3 above).

Here's a link to [a website](http://foo.bar), to a [local
doc](local-doc.html), and to a [section heading in the current
doc](#an-h2-header). Here's a footnote [^1].

[^1]: Footnote text goes here.

Tables can look like this:

size  material      color
----  ------------  ------------
9     leather       brown
10    hemp canvas   natural
11    glass         transparent

Table: Shoes, their sizes, and what they're made of

(The above is the caption for the table.) Pandoc also supports
multi-line tables:

--------  -----------------------
keyword   text
--------  -----------------------
red       Sunsets, apples, and
          other red or reddish
          things.

green     Leaves, grass, frogs
          and other things it's
          not easy being.
--------  -----------------------

A horizontal rule follows.

***

Here's a definition list:

apples
  : Good for making applesauce.
oranges
  : Citrus!
tomatoes
  : There's no "e" in tomatoe.

Again, text is indented 4 spaces. (Put a blank line between each
term/definition pair to spread things out more.)

Here's a "line block":

| Line one
|   Line too
| Line tree

and images can be specified like so:

![example image](example-image.jpg "An exemplary image")

Inline math equations go in like so: ${'$'}\omega = d\phi / dt${'$'}. Display
math should get its own line and be put in in double-dollarsigns:


And note that you can backslash-escape any punctuation characters
which you wish to be displayed literally, ex.: \`foo\`, \*bar\*, etc.

"""


@Composable
fun MinimalExampleContent() {
    MarkdownText(
        markdown = markdownContent,
        modifier = Modifier.fillMaxSize()



    )
}







