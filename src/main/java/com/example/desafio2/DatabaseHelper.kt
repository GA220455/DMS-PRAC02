package com.example.desafio2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "RestauranteMexicano.db"
        private const val DATABASE_VERSION = 1

        // Tablas
        private const val TABLE_USUARIOS = "usuarios"
        private const val TABLE_MENU = "menu"
        private const val TABLE_ORDENES = "ordenes"

        // Columnas comunes
        private const val COLUMN_ID = "id"

        // Columnas de usuarios
        private const val COLUMN_USUARIO = "usuario"
        private const val COLUMN_PASSWORD = "password"

        // Columnas de menú
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_PRECIO = "precio"
        private const val COLUMN_TIPO = "tipo"

        // Columnas de órdenes
        private const val COLUMN_USUARIO_ID = "usuario_id"
        private const val COLUMN_FECHA = "fecha"
        private const val COLUMN_TOTAL = "total"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Crear tabla de usuarios
        val createUsuariosTable = """
            CREATE TABLE $TABLE_USUARIOS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USUARIO TEXT UNIQUE,
                $COLUMN_PASSWORD TEXT
            )
        """.trimIndent()
        db.execSQL(createUsuariosTable)

        // Crear tabla de menú
        val createMenuTable = """
            CREATE TABLE $TABLE_MENU (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOMBRE TEXT,
                $COLUMN_PRECIO REAL,
                $COLUMN_TIPO TEXT
            )
        """.trimIndent()
        db.execSQL(createMenuTable)

        // Crear tabla de órdenes
        val createOrdenesTable = """
            CREATE TABLE $TABLE_ORDENES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USUARIO_ID INTEGER,
                $COLUMN_FECHA TEXT,
                $COLUMN_TOTAL REAL,
                FOREIGN KEY($COLUMN_USUARIO_ID) REFERENCES $TABLE_USUARIOS($COLUMN_ID)
            )
        """.trimIndent()
        db.execSQL(createOrdenesTable)

        // Insertar datos de ejemplo en el menú
        insertarDatosEjemplo(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // En caso de actualización de la base de datos
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MENU")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ORDENES")
        onCreate(db)
    }


    private fun insertarDatosEjemplo(db: SQLiteDatabase) {
        // Insertar alimentos
        val alimentosEjemplo = arrayOf(
            "INSERT INTO $TABLE_MENU ($COLUMN_NOMBRE, $COLUMN_PRECIO, $COLUMN_TIPO) VALUES ('Tacos al Pastor', 50.0, 'Alimento')",
            "INSERT INTO $TABLE_MENU ($COLUMN_NOMBRE, $COLUMN_PRECIO, $COLUMN_TIPO) VALUES ('Enchiladas', 60.0, 'Alimento')",
            "INSERT INTO $TABLE_MENU ($COLUMN_NOMBRE, $COLUMN_PRECIO, $COLUMN_TIPO) VALUES ('Guacamole', 30.0, 'Alimento')",
            "INSERT INTO $TABLE_MENU ($COLUMN_NOMBRE, $COLUMN_PRECIO, $COLUMN_TIPO) VALUES ('Quesadillas', 40.0, 'Alimento')",
            "INSERT INTO $TABLE_MENU ($COLUMN_NOMBRE, $COLUMN_PRECIO, $COLUMN_TIPO) VALUES ('Tamales', 25.0, 'Alimento')",
            "INSERT INTO $TABLE_MENU ($COLUMN_NOMBRE, $COLUMN_PRECIO, $COLUMN_TIPO) VALUES ('Pozole', 70.0, 'Alimento')",
            "INSERT INTO $TABLE_MENU ($COLUMN_NOMBRE, $COLUMN_PRECIO, $COLUMN_TIPO) VALUES ('Chiles en Nogada', 80.0, 'Alimento')"
        )

        // Insertar bebidas
        val bebidasEjemplo = arrayOf(
            "INSERT INTO $TABLE_MENU ($COLUMN_NOMBRE, $COLUMN_PRECIO, $COLUMN_TIPO) VALUES ('Agua de Horchata', 20.0, 'Bebida')",
            "INSERT INTO $TABLE_MENU ($COLUMN_NOMBRE, $COLUMN_PRECIO, $COLUMN_TIPO) VALUES ('Margarita', 45.0, 'Bebida')",
            "INSERT INTO $TABLE_MENU ($COLUMN_NOMBRE, $COLUMN_PRECIO, $COLUMN_TIPO) VALUES ('Tequila', 35.0, 'Bebida')"
        )

        alimentosEjemplo.forEach { db.execSQL(it) }
        bebidasEjemplo.forEach { db.execSQL(it) }
    }

    // New methods for saving and retrieving purchase history

    fun savePurchase(usuarioId: Int, fecha: String, total: Double) {
        val values = ContentValues().apply {
            put(COLUMN_USUARIO_ID, usuarioId)
            put(COLUMN_FECHA, fecha)
            put(COLUMN_TOTAL, total)
        }
        writableDatabase.insert(TABLE_ORDENES, null, values)
    }

    fun getPurchaseHistory(usuarioId: Int): List<PurchaseRecord> {
        val purchaseList = mutableListOf<PurchaseRecord>()
        val query = """
            SELECT $COLUMN_FECHA, $COLUMN_TOTAL 
            FROM $TABLE_ORDENES 
            WHERE $COLUMN_USUARIO_ID = ? 
            ORDER BY $COLUMN_FECHA DESC
        """.trimIndent()
        val cursor = readableDatabase.rawQuery(query, arrayOf(usuarioId.toString()))

        with(cursor) {
            while (moveToNext()) {
                val date = getString(getColumnIndexOrThrow(COLUMN_FECHA))
                val total = getDouble(getColumnIndexOrThrow(COLUMN_TOTAL))
                purchaseList.add(PurchaseRecord(date, total))
            }
        }
        cursor.close()
        return purchaseList
    }
}