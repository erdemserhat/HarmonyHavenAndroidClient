package com.erdemserhat.harmonyhaven.presentation.post_authentication.article.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.erdemserhat.harmonyhaven.domain.model.rest.ArticlePresentableUIModel

@Preview
@Composable
private fun ArticleScreenPreview() {
    val articlePresentableUIModelMock = ArticlePresentableUIModel(
        title = mockTitle,
        contentPreview = mockContentPreview,
        content = mockContent,
        publishDate = "Example Date",
        category = "Example Category",
        imagePath = mockImageURL

    )

    val navControllerMock:NavController = rememberNavController()


}


private val mockTitle ="5 Dakikalık Meditasyonla Zihninizi Değiştirin ve Daha Sakin Olun "

private val mockContentPreview = "Meditasyon, günlük yaşamın karmaşasından uzaklaşıp zihninizi sakinleştirmenin harika bir yoludur. Sadece beş dakika ayırarak zihinsel, duygusal ve fiziksel faydalar elde edebilirsiniz. Küçük başlayarak bu alışkanlığı hayatınıza kolayca entegre edebilirsiniz. Meditasyon, zihninizi temizlemekten çok odağınızı koruma sürecidir ve bu süreci kendiniz için rahat ve keyifli hale getirmek önemlidir. Gününüzü güzelleştirecek bir alışkanlık olarak meditasyonu deneyin ve yaşam kalitenizdeki farkı keşfedin."
private val mockContent = "## 5 Dakikalık Meditasyonla Zihninizi Değiştirin ve Daha Sakin Olun  \n" +
        "**16 Ağustos 2024 | Blog / Alışkanlık Kazanma**\n" +
        "\n" +
        "> **\"Meditasyon, her düşüncenin ve her duygunun farkında olmak, onları yargılamadan izlemek ve bu farkındalıkla sessizliğe ulaşmaktır.\"**  \n" +
        "> –Jiddu Krishnamurti\n" +
        "\n" +
        "Meditasyon, dışarıdan karmaşık görünen bir alışkanlık gibi gelebilir. Bazen sadece \"özel\" insanlar için \"özel\" bir beceri olarak algılanabilir. Ancak bu, gerçeğin tam tersi. Meditasyon, günde sadece beş dakika ayırarak herkesin ustalaşabileceği bir beceri. Üstelik bu basit alışkanlık, hayatınıza katabileceğiniz sayısız fayda sağlar.\n" +
        "\n" +
        "### Meditasyonun Faydaları Nelerdir?\n" +
        "\n" +
        "**\uD83E\uDDE0 Zihinsel Faydalar**  \n" +
        "Meditasyon, zihninizi daha sakin ve net hale getirir. Düşünceleriniz üzerinde kontrol sahibi olmanızı sağlar ve olumsuz düşünceleri daha sağlıklı bir şekilde karşılamanızı mümkün kılar. Zihninizin karmaşıklığını çözmek için bir araçtır.\n" +
        "\n" +
        "**\uD83D\uDC96 Duygusal Faydalar**  \n" +
        "Meditasyon, duygusal denginizi bulmanıza yardımcı olur. Sürekli bir iç huzuru ve sükunet sağlar; bu hisler, düzenli uygulamayla zamanla daha da güçlenir. Anksiyete, öfke ve stres gibi olumsuz duyguları hafifletir.\n" +
        "\n" +
        "**\uD83D\uDCAA Fiziksel Faydalar**  \n" +
        "Meditasyon, stresin vücut üzerindeki olumsuz etkilerini azaltır. Daha iyi bir uyku düzeni sağlar ve beyin sağlığınızı destekler. Vücudunuzu yeniden dengeleyerek hem fiziksel hem de zihinsel sağlığınıza katkıda bulunur.\n" +
        "\n" +
        "Meditasyonun faydaları bunlarla sınırlı değil. Yıllardır üzerinde çalışılan bu pratik, sağlığınıza ve genel yaşam kalitenize sayısız katkıda bulunur. Ancak, yoğun bir hayatın ortasında yeni bir alışkanlık kazanmak zor gelebilir. Peki, meditasyona nasıl başlayabilirsiniz?\n" +
        "\n" +
        "### Nasıl Başlamalı?\n" +
        "\n" +
        "**⏳ Küçük Başlayın**  \n" +
        "Meditasyona başlarken, saatlerce oturmanız gerektiğini düşünmeyin. Sadece beş dakika bile bu alışkanlığı kazanmak için yeterli. Önemli olan, az da olsa bu süreyi verimli geçirmek. Unutmayın, birkaç dakika meditasyon yapmak, hiç yapmamaktan çok daha iyidir. Zamanla bu süreyi artırabilirsiniz, ancak birçok insan beş dakika ile başlar ve bu süreyi hayatlarına entegre ederek faydalarını görür.\n" +
        "\n" +
        "Bu kısa süreyi sabah rutininize, yatmadan önceki zamana veya işe giderken sığdırabilirsiniz. Öğle arasında veya beklerken bile yapabilirsiniz. Net ve küçük hedefler koymak, alışkanlık kazanmanızı kolaylaştırır.\n" +
        "\n" +
        "**\uD83E\uDDD8\u200D♂\uFE0F Zihninizi Temizlemeye Çalışmayın**  \n" +
        "Meditasyon dendiğinde akla ilk gelen şey \"zihni temizlemek\" olur. Ancak bu, meditasyonun asıl amacı değildir. Zihninizi tamamen boşaltmak yerine, dikkatinizi nereye çekmek istediğinizi seçip, odağınızı orada tutmaya çalışmalısınız. Bu bir nefes, bir düşünce veya bir mantra olabilir.\n" +
        "\n" +
        "Dikkatinizin dağılması çok normaldir, bu meditasyon sürecinin bir parçasıdır. Odağınızı nazikçe tekrar belirlediğiniz noktaya getirin. Pratik yaptıkça bu süreç daha da kolaylaşacaktır. Meditasyonun amacı, odağınızı mükemmel bir şekilde sağlamak değil, onu korumaya çalışmaktır.\n" +
        "\n" +
        "**\uD83D\uDECB\uFE0F Rahat Olun**  \n" +
        "Meditasyonu olabildiğince rahat bir şekilde yapın. Bağdaş kurup oturmak zorunda değilsiniz; ister oturun, ister uzanın, nasıl rahat ediyorsanız öyle yapın. Gözlerinizi kapatabilir ya da açık tutabilirsiniz. Önemli olan sizin rahatınız.\n" +
        "\n" +
        "Fiziksel olarak rahat hissettiğinizde, meditasyon daha etkili olur. Aç, susamış, çok sıcak ya da soğuk hissettiğinizde odaklanmanız zor olabilir. Deneyimli meditasyoncular her yerde meditasyon yapabilirler, ancak siz başlangıçta kendinize en uygun ortamı oluşturun.\n" +
        "\n" +
        "**\uD83D\uDCAB Kendi Zamanınızı Yaratın**  \n" +
        "Meditasyon, tamamen kendiniz için ayırdığınız bir zaman dilimi olmalıdır. Bu deneyimi sizin için keyifli hale getirin. Eğer meditasyonu bir yük değil de hoş bir alışkanlık olarak görürseniz, onu hayatınıza entegre etme şansınız çok daha yüksek olur. Sevdiğiniz bir koku veya bir rahatlatıcı müzikle bu zamanı güzelleştirebilirsiniz. Bu süre, tamamen kendinizi şımartma zamanınız olsun.\n" +
        "\n" +
        "Günde beş dakikanızı keyifle meditasyona ayırırsanız, bu alışkanlığı sürdürebilir hale gelirsiniz. Meditasyonu gününüzün en güzel parçası haline getirin ve yaşam kalitenizdeki farkı görün.\n"

private val mockImageURL = "http://harmonyhaven.erdemserhat.com/sources/article_images/meditasyon_ve_farkindalik.png"

