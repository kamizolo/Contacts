package contacts

//import java.time.LocalDateTime

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.datetime.Clock
import kotlinx.datetime.toInstant
import java.io.File

//@JsonClass(generateAdapter = true)
abstract class Contact {
        open var name: String = ""
        private var number: String = ""
        var created: String? = null
        var lastEdit: String? = null
        var type = ""
        //checks and sets number
        fun setNumber(n: String) {
            val regex = "\\+?([\\w]+([ -][\\w]{2,})*|\\([\\w]+\\)([ -][\\w]{2,})*|[\\w]+[ -]\\([\\w]{2,}\\)([ -][\\w]{2,})*)".toRegex()
            if(regex.matches(n)) {
                number = n
            } else {
                println("Wrong number format!")
                number = "[no number]"
            }
        }
        //return number
        fun getNumber() : String {
            return number
        }
        //remove not needed
        open fun isPerson() : Boolean{
            return true
        }
        open fun printL() {
            println(name)
        }
        abstract fun info()
        abstract fun edit(s: String)
        fun time() :String {
            var temp = Clock.System.now().toString()
            return temp.substring(0,16)
        }
        abstract fun variableList() : MutableList<String>
        abstract fun setVariable(variable: String, value: String)
        abstract fun getVariable(variable: String) : String
    }

    class Person() : Contact() {
        override var name: String = ""
        var surname: String = ""
        var dateOfBirth = ""
        var gender = ""

        //used to create/fill class
        fun add(n: String, s: String, num: String, b: String, g: String) {
            type = "Person"
            name = n
            surname = s
            setNumber(num)
            dateOfBirth = b
            gender = g
            created = time()
            lastEdit = created
        }
        //check and set gender if valid
        fun setGenderx(g: String) {
            if(g != "M" && g != "F"){
                println("Bad gender!")
                gender = "[no data]"
            } else gender = g
        }
        //check and set date if valid
        fun setBirth(b: String) {
            try {
                val t = b.toInstant()
                dateOfBirth = b
            } catch (e: Exception) {
                println("Bade birth date!")
                dateOfBirth = "[no data]"
            }
        }
        //print class variables
        override fun info() {
            println("Name: $name")
            println("Surname: $surname")
            println("Birth date: $dateOfBirth")
            println("Gender: $gender")
            println("Number: ${getNumber()}")
            println("Time created: $created")
            println("Time last edit: $lastEdit")
        }
        //set variables
        override fun edit(field: String) {
            when (field) {
                "name" -> {
                    println("Enter $field ")
                    name = readln()
                    lastEdit = time()
                    println("The record updated!")
                }

                "surname" -> {
                    println("Enter $field ")
                    surname = readln()
                    lastEdit = time()
                    println("The record updated!")
                }

                "number" -> {
                    println("Enter $field ")
                    setNumber(readln())
                    lastEdit = time()
                    println("The record updated!")
                }
                "birth" -> {
                    println("Enter date ")
                    setBirth(readln())
                    lastEdit = time()
                    println("The record updated!")
                }
                "gender" -> {
                    println("Enter $field ")
                    setGenderx(readln())
                    lastEdit = time()
                    println("The record updated!")
                }
            }
            lastEdit = time()
        }
        //return list of variables
        override fun variableList() : MutableList<String> {
            return mutableListOf("name","surname","dateOfBirth","gender","number")
        }
        //set variable
        override fun setVariable(variable: String, value: String) {
            when(variable) {
                "name" -> {name = value}
                "surname" -> {surname = value}
                "dateOfBirth" -> setBirth(value)
                "gender" -> setGenderx(value)
                "number" -> setNumber(value)
            }
            lastEdit = time()
        }
        //return variable value
        override fun getVariable(variable: String) : String {
            when(variable) {
                "name" -> return name
                "surname" -> return surname
                "dateOfBirth" -> return dateOfBirth
                "gender" -> return gender
                "number" -> return getNumber()
            }
            return "-1"
        }
        //print name
        override fun printL() {
            println("$name $surname")
        }
    }

class Organizations() : Contact() {
    var address = ""
    override fun isPerson() : Boolean{
        return false
    }
    //set all variables
    fun add(n: String, a: String, num: String) {
        name = n
        address = a
        setNumber(num)
        type = "Organization"

        var temp = Clock.System.now().toString()
        var trans = temp.substring(0,16)
        //println(trans)
        created = time()
        lastEdit = created
    }
    //print variable info
    override fun info() {
        println("Organization name: $name")
        println("Address: $address")
        println("Number: ${getNumber()}")
        println("Time created: $created")
        println("Time last edit: $lastEdit")
    }
    //sett a field value
    override fun edit(field: String) {
        when (field) {
            "address" -> {
                println("Enter $field")
                address = readln()
            }
            "number" -> {
                println("Enter $field")
                setNumber(readln())
            }
        }
        lastEdit = time()
    }
    //list of available variables
    override fun variableList() : MutableList<String> {
        return mutableListOf("name","address","number")
    }
    //set a variable
    override fun setVariable(variable: String, value: String) {
        when(variable) {
            "name" -> {name = value}
            "address" -> {address = value}
            "number" -> setNumber(value)
        }
        lastEdit = time()
    }
    //return a variable value
    override fun getVariable(variable: String) : String {
        when(variable) {
            "name" -> return name
            "address" -> return address
            "number" -> return getNumber()
        }
        return "-1"
    }
}



    class PhoneBook {
        val records = mutableListOf<Contact>()
        //adds new contact
        fun add() {
            println("Enter the type (person, organization): ")
            val type = readln()
            if(type == "person") {
                println("Enter the name of the person: ")
                val name = readln()
                println("Enter the surname of the person: ")
                val surname = readln()
                println("Enter the birth date: ")
                var birth = readln()
                //move to a fun
                try {
                    val t = birth.toInstant()
                } catch (e: Exception) {
                    println("Bade birth date!")
                    birth = "[no data]"
                }
                println("Enter the gender (M, F)")
                var gender = readln()
                if(gender != "M" && gender != "F"){
                    println("Bad gender!")
                    gender = "[no data]"
                }
                println("Enter the number:")

                val number = readln()
                val pers = Person()
                //simplify
                pers.add(name, surname, number, birth, gender)
                records.add(pers)
                println("A record created!")
            } else if(type == "organization") {
                println("Enter the organization name:")
                val name = readln()
                println("Enter the address:")
                val address = readln()
                println("Enter the number:")
                val number = readln()
                val org = Organizations()
                org.add(name, address, number)
                records.add(org)
                println("A record created!")
            }
        }
        //returns number of entries
        fun count() {
            println("The Phone Book has ${records.size} records.")
        }
        //prints the names of entries as a list
        fun list() {
            var i = 1
            for (r in records) {
                print("${i++}. ")
                r.printL()
            }
        }
        //the list command from main
        fun info() {
            list()
            //what to do with found items
            println("\n[list] Enter action ([number], back): ")
            val x = readln()
            if (x == "back") return
            records[x.toInt() - 1].info()
            println()
            //what to do with selected item
            while (true) {
                println("[record] Enter action (edit, delete, menu): ")
                when (readln()) {
                    "edit" -> {
                        println("Select a field ${records[x.toInt() - 1].variableList().joinToString()}:}")
                        val field = readln()
                        println("enter $field")
                        val value = readln()
                        records[x.toInt() - 1].setVariable(field, value)
                        println("Saved")
                        records[x.toInt() - 1].info()
                        return
                    }
                    "delete" -> {
                        records.removeAt(x.toInt() - 1)
                        return
                    }
                    "menu" -> return
                }
            }
        }
        //searches in phonebook
        fun search() {
            while (true) {
                print("Enter search query: ")
                var regex = readln().toRegex()
                var hits = mutableListOf<Int>() //to store found indexes
                var s = ""
                for (i in records.indices) {
                    val variables = records[i].variableList()
                    for (v in variables) s += "${records[i].getVariable(v)} " //all variables as a single string
                    if (regex.containsMatchIn(s.lowercase())) hits.add(i)
                    s = ""
                }
                if (hits.size == 1) println("Found ${hits.size} result:")
                else println("Found ${hits.size} results:")

                //list of found contacts
                for (i in 0..hits.lastIndex) {
                    println("${i +1}. ${records[hits[i]].name}")
                }
                println()
                print("[search] Enter action ([number], back, again): ")
                when (val a = readln()){
                    "again" -> continue
                    "back" -> return
                    else -> { // a number
                        records[hits[a.toInt() - 1]].info()
                        println()
                        while (true) {
                            print("[record] Enter action (edit, delete, menu): ")
                            when (readln()) {
                                "edit" -> {
                                    println("Select a field ${records[hits[a.toInt() - 1]].variableList().joinToString()}:}")
                                    val field = readln()
                                    println("enter $field")
                                    val value = readln()
                                    records[hits[a.toInt() - 1]].setVariable(field, value)
                                    println("Saved")
                                    records[hits[a.toInt() - 1]].info()
                                    return
                                }
                                "delete" -> {
                                    records.removeAt(hits[a.toInt() - 1])
                                    return
                                }
                                "menu" -> return
                            }
                        }
                    }
                }
            }

        }
    }
    fun main(args: Array<String>) {
        var read = false

        val phoneBook = PhoneBook()


        val adapter = PolymorphicJsonAdapterFactory
            .of(Contact::class.java, "type")
            .withSubtype(Person::class.java, "Person")
            .withSubtype(Organizations::class.java, "Organization")

        val moshi2 = Moshi.Builder() //to write with
            .add(KotlinJsonAdapterFactory())
            .build()
        val moshi = Moshi.Builder() //to read with
            .add(adapter)
            .add(KotlinJsonAdapterFactory())
            .build()
        //write
        val personAdapter = moshi2.adapter(Person::class.java)
        val orgAdapter = moshi2.adapter(Organizations::class.java)
        //read
        val type = Types.newParameterizedType(List::class.java, Contact::class.java, Map::class.java)
        val adapt = moshi.adapter<List<Contact?>>(type)

        var file = File("")
        if (!args.isEmpty()) file = File(args[0])
        if (file.exists()) { // checks if file exists
            read = true

            val lines = file.readText()
            val a = adapt.fromJson(lines)
            for (h in a!!) {
                if (h is Person || h is Organizations) phoneBook.records.add(h)
            }

        } else println("No file found for reading found")

        //main loop
        while (true) {
            print("[menu] Enter action (add, list, search, count, exit): ")
            val input = readln()
            if (input == "add") phoneBook.add()
            if (input == "list") phoneBook.info()
            if (input == "count") phoneBook.count()
            if (input == "search") phoneBook.search()
            if (input == "exit") break
            println()
        }

        if (!read) println("No file found, creating file")
        else println("updating ${args[0]}")

        //write to file
        file.writeText("[")
        for (i in 0 until phoneBook.records.size) {
            if(i == phoneBook.records.size - 1) {
                if (phoneBook.records[i] is Person) {
                    file.appendText(personAdapter.toJson(phoneBook.records[i] as Person))
                } else if (phoneBook.records[i] is Organizations) {
                    file.appendText(orgAdapter.toJson(phoneBook.records[i] as Organizations))
                }
            } else {
                if (phoneBook.records[i] is Person) {
                    file.appendText("${personAdapter.toJson(phoneBook.records[i] as Person)},")
                } else if (phoneBook.records[i] is Organizations) {
                    file.appendText("${orgAdapter.toJson(phoneBook.records[i] as Organizations)},")
                }
            }
        }
        file.appendText("]")
    }

/*
todo: unify jason read write.
todo: to many functions for adding variables to a contact
todo: add logic for wrong inputs
todo: remove isPerson not needed
 */