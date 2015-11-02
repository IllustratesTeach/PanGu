package nirvana.hall.v62

/**
 * constants for ancient system
 * @author <a href="mailto:jcai@ganshane.com">Jun Tsai</a>
 * @since 2015-10-30
 */
object AncientConstants {

  val READY                    = 0
  val WORKING                  = 1
  val FINISHED                 = 2
  val ERRDATA                  = 3
  val DELETED                  = 4
  val WAITCENSOR               = 5
  val CANNOTSUBMIT             = 6
  val CHECKED				           = 7
  val CHECKING			           = 8
  val WAITRECHECK			         = 9
  val RECHECKING			         = 10
  val RECHECKED			           = 11
  val NOQUEUE				           = 99
  val TOTAL				             = 98

  val TTMATCH                  = 0
  val TLMATCH                  = 1
  val LTMATCH                  = 2
  val LLMATCH                  = 3
  val LLSEARCH                 = LLMATCH
  val TTSEARCH                 = TTMATCH
  /* Define get all data macro, when parameter fgnum is GETALL */
  val GAFIS_GETALL		         = -1
  val TTQUERY		               = "TT"
  val LLQUERY		               = "LL"
  val LTQUERY		               = "LT"
  val TLQUERY		               = "TL"

}
