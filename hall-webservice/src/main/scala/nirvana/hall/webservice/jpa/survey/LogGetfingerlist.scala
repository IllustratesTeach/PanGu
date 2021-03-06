package nirvana.hall.webservice.jpa.survey

// Generated 2018-7-24 14:53:35 by Stark Activerecord generator 4.3.1.Final


import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance};

/**
 * LogGetfingerlist generated by stark activerecord generator
 */
object LogGetfingerlist extends ActiveRecordInstance[LogGetfingerlist]

@Entity
@Table(name="LOG_GETFINGERLIST"
)
class LogGetfingerlist extends ActiveRecord {


    @Id
    @Column(name = "PK_ID", unique = true, nullable = false, length = 32)
    var pkId: java.lang.String = _
    @Column(name = "USERID", length = 20)
    var userid: java.lang.String = _
    @Column(name = "ASJFSDD_XZQHDM", precision = 12, scale = 0)
    var asjfsddXzqhdm: java.lang.Long = _
    @Column(name = "XCKYBH", length = 23)
    var xckybh: java.lang.String = _
    @Column(name = "ZZHWLX", length = 3)
    var zzhwlx: java.lang.String = _
    @Column(name = "KSSJ")
    var kssj: java.util.Date = _
    @Column(name = "JSSJ")
    var jssj: java.util.Date = _
    @Column(name = "KS", precision = 65535, scale = 32767)
    var ks: java.lang.Integer = _
    @Column(name = "JS", precision = 65535, scale = 32767)
    var js: java.lang.Integer = _
    @Column(name = "CALLTIME")
    var calltime: java.util.Date = _
    @Column(name = "RETURNTIME")
    var returntime: java.util.Date = _
    @Column(name = "RETURNXML")
    var returnxml: java.lang.String = _
    @Column(name = "ERRORMSG")
    var errormsg: java.lang.String = _


	
    def this(pkId:java.lang.String) {
        this()
        this.pkId = pkId
    }
    def this(pkId:java.lang.String, userid:java.lang.String, asjfsddXzqhdm:java.lang.Long, xckybh:java.lang.String, zzhwlx:java.lang.String, kssj:java.util.Date, jssj:java.util.Date, ks:java.lang.Integer, js:java.lang.Integer, calltime:java.util.Date, returntime:java.util.Date, returnxml:java.lang.String, errormsg:java.lang.String) {
       this()
       this.pkId = pkId
       this.userid = userid
       this.asjfsddXzqhdm = asjfsddXzqhdm
       this.xckybh = xckybh
       this.zzhwlx = zzhwlx
       this.kssj = kssj
       this.jssj = jssj
       this.ks = ks
       this.js = js
       this.calltime = calltime
       this.returntime = returntime
       this.returnxml = returnxml
       this.errormsg = errormsg
    }
   




}


