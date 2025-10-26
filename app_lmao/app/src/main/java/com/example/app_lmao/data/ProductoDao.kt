// app/src/main/java/com/example/app_lmao/data/ProductoDao.kt
package com.example.app_lmao.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.app_lmao.model.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductoDao {

    @Query("SELECT * FROM productos ORDER BY nombre ASC")
    fun getAll(): Flow<List<Producto>> // Usar Flow para reactividad

    @Query("SELECT * FROM productos WHERE id = :id")
    fun getById(id: Int): Flow<Producto?>

    @Query("SELECT * FROM productos WHERE nombre LIKE '%' || :query || '%' OR descripcion LIKE '%' || :query || '%'")
    fun search(query: String): Flow<List<Producto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(producto: Producto)

    @Update
    suspend fun update(producto: Producto)

    @Query("DELETE FROM productos WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(productos: List<Producto>)
}