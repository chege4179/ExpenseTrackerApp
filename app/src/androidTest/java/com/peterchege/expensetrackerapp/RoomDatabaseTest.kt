package com.peterchege.expensetrackerapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.peterchege.expensetrackerapp.core.room.dao.TransactionEntityDao
import com.peterchege.expensetrackerapp.core.room.database.ExpenseTrackerAppDatabase
import com.peterchege.expensetrackerapp.core.room.entities.TransactionEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class RoomDatabaseTest {
    private lateinit var transactionDao: TransactionEntityDao
    private lateinit var db: ExpenseTrackerAppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ExpenseTrackerAppDatabase::class.java
        ).build()
        transactionDao = db.transactionEntityDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun addTransaction() = runBlocking {
        val transaction = TransactionEntity(
            transactionId = "test",
            transactionName = "test",
            transactionAmount = 0,
            transactionCategoryId = "test",
            transactionCreatedOn = "test",
            transactionUpdatedAt = "test",
            transactionUpdatedOn = "test",
            transactionCreatedAt = "test",
        )
        transactionDao.insertTransaction(transactionEntity = transaction)
        val dummyTransaction = transactionDao.getTransactionById("test")
        assertNotNull(dummyTransaction)
        assert(dummyTransaction?.transactionName == transaction.transactionName)
    }

    @Test
    @Throws(Exception::class)
    fun deleteTransaction() = runBlocking {
        val transaction = TransactionEntity(
            transactionId = "test",
            transactionName = "test",
            transactionAmount = 0,
            transactionCategoryId = "test",
            transactionCreatedOn = "test",
            transactionUpdatedAt = "test",
            transactionUpdatedOn = "test",
            transactionCreatedAt = "test",
        )
        transactionDao.insertTransaction(transactionEntity = transaction)
        transactionDao.deleteAllTransactions()
        val allTransactions = transactionDao.getTransactions().first()
        assert(allTransactions.isEmpty())

    }
}