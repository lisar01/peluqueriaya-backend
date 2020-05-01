package ar.edu.unq.peluqueriayabackend.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
enum class ServicioType(val nombre: String, val logoURL: String) {
    CORTE("Corte", "https://www.stylist.co.uk/images/app/uploads/2020/04/29122843/how-to-cut-your-own-hair-256x256.jpg?w=256&h=256&fit=max&auto=format%2Ccompress"),
    ALISADO("Alisado", "https://www.artandcare.es/wp-content/uploads/2018/07/plan_crop1492267007450.jpg_539665225.jpg"),
    UNAS("Uñas", "https://pbs.twimg.com/profile_images/649751439343022081/qmlUziul_400x400.jpg"),
    TENIDO("Teñido", "https://www.stylist.co.uk/images/app/uploads/2018/07/01120637/stocksy_txp0bc0530bwe0200_large_878556-crop-1533121633-1363x1363.jpg?w=256&h=256&fit=max&auto=format%2Ccompress"),
    PEINADO("Peinado", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRMpNwFZSEMLUkTuSBUkINn9K3Od7Hr2bUO3g6p-hD4sWWYOG_p&usqp=CAU"),
    TRATAMIENTO("Tratamiento", "https://us.123rf.com/450wm/na2xa/na2xa1801/na2xa180104425/94233546-happy-woman-with-a-towel-on-her-head-applied-a-mask-to-clean-the-skin-of-the-face.jpg?ver=6"),
    RECOGIDO("Recogido", "https://www.cassandrakennedy.com/wp-content/uploads/2014/04/16-569-page/half-up-half-down.jpg"),
    MECHAS("Mechas", "https://i.pinimg.com/474x/1b/f4/9f/1bf49f475beafcd06087494c087d3c0d.jpg"),
    AFEITADO("Afeitado", "https://img.grouponcdn.com/deal/6PvF8pgqqTWBVMUCfN6cXi/487471227-3000x1800/v1/c700x420.jpg"),
    BARBERIA("Barberia", "https://s3.amazonaws.com/my89-frame-annotation/public/images_256/shaving_79.jpg"),
    OTROS("Otros", "https://media.timeout.com/images/102980157/630/472/image.jpg");

    @JsonProperty("id")
    fun id() = this.name

}
