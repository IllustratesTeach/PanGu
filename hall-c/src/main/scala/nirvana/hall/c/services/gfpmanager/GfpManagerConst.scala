package nirvana.hall.c.services.gfpmanager

import nirvana.hall.c.annotations.Length
import nirvana.hall.c.services.AncientData

/**
 * Created by songpeng on 16/5/18.
 */
object GfpManagerConst {

  class Gf_AssociateGroupInfo extends AncientData {
    @Length(32)
    var szGroupID: String = _;
    //	szPersonID[32];			// 重卡编号
    var nTprCardCnt: Int = _;
    // 捺印重卡个数
    var nLatCardCnt: Int = _;
  }//GF_ASSOCIATEGROUPINFO;
}
