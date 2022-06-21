import org.litote.kmongo.*
import ru.altmanea.elem.model.TestElemDTO

val client = KMongo
    .createClient("mongodb://docker:mongopw@localhost:49153")
val mongoDatabase = client.getDatabase("test")

fun main(){
    val testCol = mongoDatabase.getCollection<TestElemDTO>()
    testCol.drop()
    testCol.insertOne(TestElemDTO("", 0L))
    print(testCol.find().toList())
}