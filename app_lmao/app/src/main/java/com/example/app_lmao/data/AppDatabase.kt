// app/src/main/java/com/example/app_lmao/data/AppDatabase.kt
package com.example.app_lmao.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.app_lmao.model.Producto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Producto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productoDao(): ProductoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pasteleria_database"
                )
                .addCallback(DatabaseCallback(context))
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DatabaseCallback(private val context: Context) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(database.productoDao())
                }
            }
        }

        // Pre-poblar la BD con los datos del caso
        suspend fun populateDatabase(productoDao: ProductoDao) {
            val productosDelCaso = listOf(
                Producto(codigo = "TC001", nombre = "Torta Cuadrada de Chocolate", descripcion = "Deliciosa torta de chocolate con capas de ganache y un toque de avellanas.", precio = 45000.0, stock = 10, categoria = "Tortas Cuadradas"),
                Producto(codigo = "TC002", nombre = "Torta Cuadrada de Frutas", descripcion = "Una mezcla de frutas frescas y crema chantilly sobre un suave bizcocho de vainilla.", precio = 50000.0, stock = 8, categoria = "Tortas Cuadradas"),
                Producto(codigo = "TT001", nombre = "Torta Circular de Vainilla", descripcion = "Bizcocho de vainilla clásico relleno con crema pastelera y cubierto con un glaseado dulce.", precio = 40000.0, stock = 12, categoria = "Tortas Circulares"),
                Producto(codigo = "TT002", nombre = "Torta Circular de Manjar", descripcion = "Torta tradicional chilena con manjar y nueces.", precio = 42000.0, stock = 7, categoria = "Tortas Circulares"),
                Producto(codigo = "PI001", nombre = "Mousse de Chocolate", descripcion = "Postre individual cremoso y suave, hecho con chocolate de alta calidad.", precio = 5000.0, stock = 20, categoria = "Postres Individuales"),
                Producto(codigo = "PI002", nombre = "Tiramisú Clásico", descripcion = "Un postre italiano individual con capas de café, mascarpone y cacao.", precio = 5500.0, stock = 15, categoria = "Postres Individuales"),
                Producto(codigo = "PSA001", nombre = "Torta Sin Azúcar de Naranja", descripcion = "Torta ligera y deliciosa, endulzada naturalmente.", precio = 48000.0, stock = 5, categoria = "Productos Sin Azúcar"),
                Producto(codigo = "PG001", nombre = "Brownie Sin Gluten", descripcion = "Rico y denso, este brownie es perfecto para quienes necesitan evitar el gluten.", precio = 4000.0, stock = 18, categoria = "Productos Sin Gluten"),
                Producto(codigo = "PV001", nombre = "Torta Vegana de Chocolate", descripcion = "Torta de chocolate húmeda y deliciosa, hecha sin productos de origen animal.", precio = 50000.0, stock = 6, categoria = "Productos Vegana")
            )
            productoDao.insertAll(productosDelCaso)
        }
    }
}