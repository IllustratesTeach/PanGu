package nirvana.hall.orm.internal

import nirvana.hall.orm.services.EntityManagerCreator
import org.apache.tapestry5.ioc.ObjectCreator
import org.apache.tapestry5.ioc.services.Builtin
import org.apache.tapestry5.ioc.services.PlasticProxyFactory
import org.apache.tapestry5.plastic._
import org.hibernate.jpa.HibernateEntityManager
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import java.lang.reflect.Method

/**
 * delegate EntityManagerFactory
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2016-01-02
 *
 */
object EntityManagerCreatorImpl {
  private val CREATE_OBJECT: Method = PlasticUtils.getMethod(classOf[ObjectCreator[_]], "createObject")
}
class EntityManagerCreatorImpl(entityManagerFactory: EntityManagerFactory, @Builtin plasticProxyFactory: PlasticProxyFactory) extends EntityManagerCreator {
  private val instance = plasticProxyFactory.createProxy(classOf[HibernateEntityManager], new PlasticClassTransformer() {
    def transform(plasticClass: PlasticClass) {
      val objectCreatorField: PlasticField = plasticClass.introduceField(classOf[ObjectCreator[_]], "creator").injectFromInstanceContext
      val isInit: PlasticField = plasticClass.introduceField(java.lang.Boolean.TYPE.getName, "isInit")
      val delegateMethod: PlasticMethod = plasticClass.introducePrivateMethod(classOf[EntityManager].getName, "delegate", null, null)
      delegateMethod.changeImplementation(new InstructionBuilderCallback() {
        def doBuild(builder: InstructionBuilder) {
          builder.loadThis.loadConstant(java.lang.Boolean.TRUE)
          builder.putField(plasticClass.getClassName, isInit.getName, isInit.getTypeName)
          builder.loadThis.getField(objectCreatorField)
          builder.invoke(EntityManagerCreatorImpl.CREATE_OBJECT)
          builder.checkcast(classOf[EntityManager]).returnResult
        }
      })
      for (method <- classOf[EntityManager].getMethods) {
        if (method.getName == "close") {
          val plasticMethod: PlasticMethod = plasticClass.introduceMethod(method)
          plasticMethod.changeImplementation(new InstructionBuilderCallback() {
            def doBuild(builder: InstructionBuilder) {
              builder.loadThis.getField(isInit)
              builder.when(Condition.ZERO, new InstructionBuilderCallback() {
                def doBuild(builder: InstructionBuilder) {
                  builder.returnResult
                }
              })
              builder.loadThis
              builder.invokeSpecial(plasticClass.getClassName, delegateMethod.getDescription)
              builder.loadArguments
              builder.invokeInterface(classOf[EntityManager].getName, plasticMethod.getDescription.returnType, method.getName, plasticMethod.getDescription.argumentTypes:_*)
              builder.returnResult
            }
          })
        }
        else if (method.getName == "isOpen") {
          val plasticMethod: PlasticMethod = plasticClass.introduceMethod(method)
          plasticMethod.changeImplementation(new InstructionBuilderCallback() {
            def doBuild(builder: InstructionBuilder) {
              builder.loadThis.getField(isInit)
              builder.when(Condition.NON_ZERO, new InstructionBuilderCallback() {
                def doBuild(builder: InstructionBuilder) {
                  builder.loadThis
                  builder.invokeSpecial(plasticClass.getClassName, delegateMethod.getDescription)
                  builder.loadArguments
                  builder.invokeInterface(classOf[EntityManager].getName, plasticMethod.getDescription.returnType, method.getName, plasticMethod.getDescription.argumentTypes:_*)
                  builder.returnResult
                }
              })
              builder.loadConstant(java.lang.Boolean.FALSE)
              builder.returnResult
            }
          })
        }
        else {
          plasticClass.introduceMethod(method).delegateTo(delegateMethod)
        }
      }
    }
  })
  private[internal] class InternalEntityManagerCreator extends ObjectCreator[EntityManager] {
    private var _entityManager: EntityManager = null
    def createObject: EntityManager = {
      if (_entityManager == null) {
        _entityManager = entityManagerFactory.createEntityManager
      }
      return _entityManager
    }
  }

  def createEntityManager: EntityManager = {
    instance.`with`(classOf[ObjectCreator[_]], new InternalEntityManagerCreator).newInstance
  }
}

