package nirvana.hall.api.internal.blob.coder;

import java.util.Map;

import nirvana.hall.api.internal.blob.entity.CNTPCardText;
import nirvana.hall.api.internal.blob.util.AFISEntityHelper;
import nirvana.hall.api.internal.blob.util.AFISHelper;

public class CNTPCardTextCnv {
	public static CNTPCardText toEntity(TPCardTextObject obj) {
		if ((obj == null) || (obj.text == null)) {
			return null;
		}
		TextEntityObject text = obj.text;
		if ((AFISHelper.isEmpty(text.strItemList))
				&& (AFISHelper.isEmpty(text.itemList))) {
			return null;
		}
		Map maps = text.strItemList;
		if (AFISHelper.isEmpty(maps)) {
			maps = CNTPCardTextIdCnv.toName(text.itemList);
		}
		if (AFISHelper.isEmpty(maps)) {
			return null;
		}

		CNTPCardText ent = (CNTPCardText) AFISEntityHelper.toEntity(CNTPCardText.class, maps);

		ent.setId(Long.valueOf(obj.id));
		ent.setDbId(obj.dbId);
		ent.setUpdateTime(obj.updateTime);
		ent.setUpdateUser(obj.updateUser);

		return ent;
	}

	public static TPCardTextObject toObject(CNTPCardText ent) {
		TPCardTextObject obj = new TPCardTextObject();
		obj.id = ent.getId().longValue();
		obj.dbId = ent.getDbId();
		obj.updateUser = ent.getUpdateUser();
		obj.updateTime = ent.getUpdateTime();
		obj.text = new TextEntityObject();
		obj.text.strItemList = AFISEntityHelper.fromEntity(ent);

		obj.text.itemList = CNTPCardTextIdCnv.toId(obj.text.strItemList);
		return obj;
	}
}