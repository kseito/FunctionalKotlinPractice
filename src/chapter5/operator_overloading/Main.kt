package chapter5.operator_overloading

fun main() {
    val talbot = Wolf("Talbot")
    val northPack: Pack = talbot + Wolf("Big Bertha")
    northPack.members.forEach { (name, _) ->
        println(name)
    }

    val biggerPack = northPack + Wolf("Bad Wolf")
    biggerPack.members.forEach { (name, _) ->
        println(name)
    }

    println(talbot(WolfActions.SLEEP))

    val badWolf = biggerPack["Bad Wolf"]
    println(badWolf?.name)

    talbot[WolfRelationships.FRIEND] = badWolf!!

    println(!talbot)
}

class Wolf(val name: String) {
    operator fun plus(wolf: Wolf) = Pack(mapOf(name to this, wolf.name to wolf))

    operator fun invoke(action: WolfActions) = when(action) {
        WolfActions.SLEEP -> "$name is sleeping"
        WolfActions.WALK -> "$name is walking"
        WolfActions.BITE -> "$name is biting"
    }
}

class Pack(val members: Map<String, Wolf>)
operator fun Pack.plus(wolf: Wolf) = Pack(members.toMutableMap() + (wolf.name to wolf))

enum class WolfActions {
    SLEEP, WALK, BITE
}

operator fun Pack.get(name: String) = members[name]

enum class WolfRelationships {
    FRIEND, SIBLING, ENEMY, PARTNER
}

operator fun Wolf.set(relationships: WolfRelationships, wolf: Wolf) {
    println("${wolf.name} is my new $relationships")
}

operator fun Wolf.not() = "$name is angry!!!"
