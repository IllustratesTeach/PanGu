package nirvana.hall.api.internal.jdbc

import org.apache.tapestry5.ioc.annotations.PostInjection
import org.springframework.transaction.annotation.{ Isolation, Propagation }
import org.springframework.transaction.interceptor.DefaultTransactionAttribute
import org.springframework.transaction.support.TransactionCallback
import org.springframework.transaction.{ PlatformTransactionManager, TransactionDefinition, TransactionException, TransactionStatus }

/**
 * Trait that simplifies functional transaction demarcation and transaction exception handling.
 *
 * The central entry point is the `transactional` function. This trait handles the transaction lifecycle and possible
 * exceptions such that neither the function nor the calling code needs to explicitly handle transactions.
 *
 * @author Arjen Poutsma
 * @since 1.0
 */
trait TransactionManagement {

  /**
   * The transaction management strategy to be used
   */
  private var transactionManager: PlatformTransactionManager = _
  @PostInjection
  def setupTransactionManager(platformTransactionManager: PlatformTransactionManager): Unit = {
    this.transactionManager = platformTransactionManager
  }

  /**
   * Execute the given function within a transaction.
   *
   * @tparam T the return type of the function
   * @param function the function to be executed transactionally
   * @param propagation the propagation behavior. Defaults to `REQUIRED`
   * @param isolation the isolation level. Defaults to `DEFAULT`
   * @param readOnly  whether to optimize as a read-only transaction. Defaults to `false`.
   * @param qualifier qualifier value for the specified transaction. Defaults to none.
   * @param timeout timeout for this transaction. Defaults to the default timeout of the underlying transaction
   * system.
   * @return a result object returned by the function
   * @throws TransactionException in case of initialization, rollback, or system errors
   */
  def transactional[T](propagation: Propagation = Propagation.REQUIRED,
                       isolation: Isolation = Isolation.DEFAULT,
                       readOnly: Boolean = false,
                       qualifier: String = "",
                       timeout: Int = TransactionDefinition.TIMEOUT_DEFAULT)(function: => T): T = {

    val transactionAttribute = new DefaultTransactionAttribute()
    transactionAttribute.setPropagationBehavior(propagation.value())
    transactionAttribute.setIsolationLevel(isolation.value())
    transactionAttribute.setTimeout(timeout)
    transactionAttribute.setReadOnly(readOnly)
    transactionAttribute.setQualifier(qualifier)

    val template = new org.springframework.transaction.support.TransactionTemplate(transactionManager,
      transactionAttribute)
    template.execute(new TransactionCallback[T] {
      def doInTransaction(status: TransactionStatus) = function
    })
  }

}
