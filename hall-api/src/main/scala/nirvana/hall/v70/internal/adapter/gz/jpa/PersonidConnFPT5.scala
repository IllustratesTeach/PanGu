package nirvana.hall.v70.internal.adapter.gz.jpa

import javax.persistence.{Column, Entity, Id, Table}

import stark.activerecord.services.{ActiveRecord, ActiveRecordInstance};

object PersonidConnFPT5 extends ActiveRecordInstance[PersonidConnFPT5]

@Entity
@Table(name = "PERSONID_CONN_FPT5"
)
class PersonidConnFPT5 extends ActiveRecord {

  @Id
  @Column(name = "PERSONID", unique = true, nullable = false, length = 23)
  var personid: java.lang.String = _
  @Column(name = "PERSONID_FPT5", length = 23)
  var personid_FPT5: java.lang.String = _


  def this(personid: java.lang.String) {
    this()
    this.personid = personid
  }

  def this(personid: java.lang.String, personid_FPT5: java.lang.String) {
    this()
    this.personid = personid
    this.personid_FPT5 = personid_FPT5

  }


}


