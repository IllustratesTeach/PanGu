package nirvana.hall.v70.internal.blob.coder;

import nirvana.hall.v70.internal.blob.entity.TPCardExtraInfo;

public class TPExtraInfoCnv {
	public static TPCardExtraInfo toEntity(TPExtraInfoObject obj) {
		TPCardExtraInfo ent = new TPCardExtraInfo();

		ent.setId(obj.id);
		ent.setMisConnectId(obj.misConnectId);
		ent.setMobid(obj.mobid);
		ent.setRcn(obj.rcn);
		return ent;
	}

	public static TPExtraInfoObject toObject(TPCardExtraInfo ent) {
		TPExtraInfoObject obj = new TPExtraInfoObject();

		obj.id = ent.getId();
		obj.misConnectId = ent.getMisConnectId();
		obj.mobid = ent.getMobid();
		obj.rcn = ent.getRcn();
		return obj;
	}
}