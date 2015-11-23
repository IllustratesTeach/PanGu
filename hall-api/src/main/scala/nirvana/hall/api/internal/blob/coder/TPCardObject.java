package nirvana.hall.api.internal.blob.coder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nirvana.hall.api.internal.blob.entity.CNTPCardText;
import nirvana.hall.api.internal.blob.entity.TPBlobData;
import nirvana.hall.api.internal.blob.entity.TPBlobUniqId;
import nirvana.hall.api.internal.blob.entity.TPCardAdmin;
import nirvana.hall.api.internal.blob.entity.TPFgpMain;
import nirvana.hall.api.internal.blob.entity.TextObject;
import nirvana.hall.api.internal.blob.util.AFISHelper;
import nirvana.hall.api.internal.blob.util.AFISLocHelper;
import nirvana.hall.api.internal.blob.util.CompHelper;

public class TPCardObject implements Serializable, Cloneable {
	private static final long serialVersionUID = 2L;
	private TPCardAdmin admin;
	private TextObject text;
	private List<TPBlobData> blobList;
	private List<TPFgpMain> fgpList;
	private TPCardUpdateInfo modLog;
	private List<TPCardSysConnectNum> connNum;

	public TPCardAdmin getAdmin() {
		return this.admin;
	}

	public void setAdmin(TPCardAdmin admin) {
		this.admin = admin;
	}

	public TextObject getText() {
		return this.text;
	}

	public void setText(TextObject text) {
		this.text = text;
	}

	public List<TPBlobData> getBlobList() {
		return this.blobList;
	}

	public void setBlobList(List<TPBlobData> blobList) {
		this.blobList = blobList;
	}

	public List<TPBlobData> getBlob(byte bty, byte fgp, byte viewId,
			byte groupId, byte lobType) {
		if (this.blobList == null)
			return null;
		List blobGot = new ArrayList();

		for (TPBlobData lob : this.blobList) {
			TPBlobUniqId uid = lob.getBlobUniqId();
			if (((bty >= 0) && (uid.getBty().byteValue() != bty))
					|| ((fgp >= 0) && (uid.getFgp().shortValue() != fgp))
					|| ((viewId >= 0) && (uid.getViewId().byteValue() != viewId))
					|| ((groupId >= 0) && (uid.getGroupId().byteValue() != groupId))
					|| ((lobType >= 0) && (uid.getLobType().byteValue() != lobType)))
				continue;
			blobGot.add(lob);
		}
		return blobGot.size() == 0 ? null : blobGot;
	}

	public List<TPBlobData> getBlob(byte bty, short fgp, byte viewId) {
		if (this.blobList == null)
			return null;
		List blobGot = new ArrayList();

		for (TPBlobData lob : this.blobList) {
			TPBlobUniqId uid = lob.getBlobUniqId();
			if (((bty >= 0) && (uid.getBty().byteValue() != bty))
					|| ((fgp >= 0) && (uid.getFgp().shortValue() != fgp))
					|| ((viewId >= 0) && (uid.getViewId().byteValue() != viewId)))
				continue;
			blobGot.add(lob);
		}
		return blobGot.size() == 0 ? null : blobGot;
	}

	public boolean exist(byte bty, short fgp, byte viewId) {
		if (this.blobList == null)
			return false;

		for (TPBlobData lob : this.blobList) {
			TPBlobUniqId uid = lob.getBlobUniqId();
			if (((bty < 0) || (uid.getBty().byteValue() == bty))
					&& ((fgp < 0) || (uid.getFgp().shortValue() == fgp))
					&& ((viewId < 0) || (uid.getViewId().byteValue() == viewId))) {
				return true;
			}
		}
		return false;
	}

	public TPBlobData getFirstBlob(byte bty, byte fgp, byte viewId,
			byte groupId, byte lobType) {
		List blobGot = getBlob(bty, fgp, viewId, groupId, lobType);
		if (blobGot == null)
			return null;
		return (TPBlobData) blobGot.get(0);
	}

	public void setFgpList(List<TPFgpMain> fgpList) {
		this.fgpList = fgpList;
	}

	public List<TPFgpMain> getFgpList() {
		return this.fgpList;
	}

	public void distriBlob() {
		if ((this.blobList == null) || (this.fgpList == null))
			return;

		for (TPFgpMain fm : this.fgpList) {
			fm.setBlobData(getBlob(fm.getBty(), fm.getFgp(), fm.getViewId()));
		}
	}

	public void setId(long id) {
		if (this.admin != null) {
			this.admin.setId(id);
		}

		if (this.blobList != null) {
			for (TPBlobData blob : this.blobList) {
				blob.getBlobUniqId().setTpCardId(id);
			}
		}

		if (this.fgpList != null) {
			for (TPFgpMain fgpMain : this.fgpList) {
				fgpMain.setTpCardId(id);
			}
		}

		if (this.text != null) {
			this.text.setId(Long.valueOf(id));
			Iterator it = this.text.getEntityList().iterator();
			while (it.hasNext()) {
				Object obj = it.next();

				if ((obj instanceof CNTPCardText)) {
					((CNTPCardText) obj).setId(Long.valueOf(id));
				}
				if (!(obj instanceof TPCardTextObject))
					continue;
				TPCardTextObject txt = (TPCardTextObject) obj;
				txt.id = id;
				if ((txt.text == null) || (txt.text.strItemList == null))
					continue;
				txt.text.strItemList.put("ID", Long.valueOf(id));
			}

		}

		if (this.modLog != null) {
			this.modLog.setTpCardId(id);
		}
	}

	public void setDtid(short dbid) {
		if (this.admin != null) {
			if (this.admin.getDbid() != dbid)
				this.admin.setDbid(dbid);
			if (this.admin.getTid() != 20)
				this.admin.setTid((short) 20);
		}

		if (this.blobList != null) {
			for (TPBlobData lob : this.blobList) {
				if (lob.getDbid() == dbid)
					continue;
				lob.setDbid(dbid);
			}
		}
		if (this.fgpList != null) {
			for (TPFgpMain fm : this.fgpList) {
				if (fm.getDbid() == dbid)
					continue;
				fm.setDbid(dbid);
			}
		}
		if (this.text != null) {
			Iterator it = this.text.getEntityList().iterator();
			while (it.hasNext()) {
				Object obj = it.next();

				if (!(obj instanceof CNTPCardText))
					continue;
				((CNTPCardText) obj).setDbId(Short.valueOf(dbid));
			}

		}

		if (this.modLog != null) {
			this.modLog.setDbId(Short.valueOf(dbid));
		}
	}

	public boolean checkDtid(short dbid) {
		if (this.admin != null) {
			if (this.admin.getDbid() != dbid)
				return false;
			if (this.admin.getTid() != 20)
				return false;
		}
		if (this.blobList != null) {
			for (TPBlobData lob : this.blobList) {
				if (lob.getDbid() != dbid)
					return false;
			}
		}
		if (this.fgpList != null) {
			for (TPFgpMain fm : this.fgpList) {
				if (fm.getDbid() != dbid)
					return false;
			}
		}
		return true;
	}

	public TPCardUpdateInfo getModLog() {
		return this.modLog;
	}

	public void setModLog(TPCardUpdateInfo modLog) {
		this.modLog = modLog;
	}

	private boolean addFgp(List<TPFgpMain> fgpList, TPBlobData blob) {
		TPBlobUniqId blobId = blob.getBlobUniqId();

		for (TPFgpMain fgp : fgpList) {
			if ((CompHelper.equals(Byte.valueOf(fgp.getBty()), blobId.getBty()))
					&& (CompHelper.equals(Byte.valueOf(fgp.getViewId()), blobId
							.getViewId()))
					&& (CompHelper.equals(Short.valueOf(fgp.getFgp()), blobId
							.getFgp())))
				return false;
		}

		TPFgpMain fgp = new TPFgpMain();
		fgp.setBty(blobId.getBty().byteValue());
		fgp.setViewId(blobId.getViewId().byteValue());
		fgp.setFgp(blobId.getFgp().shortValue());
		if (blob.getBlobUniqId().getTpCardId() != null)
			fgp.setTpCardId(blob.getBlobUniqId().getTpCardId().longValue());

		fgp.setCreateUser(blob.getCreateUser());
		fgp.setCreateTime(blob.getCreateTime());
		fgp.setUpdateUser(blob.getUpdateUser());
		fgp.setUpdateTime(blob.getUpdateTime());
		fgp.setDbid(blob.getDbid());
		fgpList.add(fgp);
		return true;
	}

	public void checkFgpList() {
		if (this.fgpList != null)
			return;

		if (this.blobList != null) {
			List fgpList = new ArrayList();
			for (TPBlobData blob : this.blobList) {
				addFgp(fgpList, blob);
			}

			if (!fgpList.isEmpty()) {
				this.fgpList = fgpList;
			}
		}
	}

	public void checkFgpList(List<TPFgpMain> fgpList) {
		if (this.blobList == null)
			return;
		if (fgpList == null)
			fgpList = new ArrayList();

		for (TPBlobData blob : this.blobList) {
			addFgp(fgpList, blob);
		}
	}

	public Object clone() throws CloneNotSupportedException {
		try {
			return AFISLocHelper.clone(this);
		} catch (Exception e) {
		}
		throw new CloneNotSupportedException(
				"cn.eastgf.gafis.tp.bzo.TPCardObject");
	}

	public void initId() {
		this.admin.setId(0L);

		if (!AFISHelper.isEmpty(this.blobList)) {
			for (TPBlobData lob : this.blobList) {
				lob.setId(0L);
			}
		}

		if (!AFISHelper.isEmpty(this.fgpList)) {
			for (TPFgpMain fgp : this.fgpList) {
				if (fgp == null)
					continue;
				fgp.setId(0L);
			}
		}

		if (this.modLog != null) {
			this.modLog.setTpCardId(0L);
		}

		if (this.text != null) {
			this.text.setId(null);
		}
	}

	public void buildBtyFlag() {
		if (this.admin.getBtyFlag() != 0)
			return;
		if (this.blobList == null)
			return;

		int btyFlag = 0;

		for (TPBlobData lob : this.blobList) {
			switch (lob.getBlobUniqId().getBty().byteValue()) {
			case 1:
				btyFlag |= 1;
				break;
			case 2:
				btyFlag |= 1;
				break;
			case 4:
				btyFlag |= 1;
				break;
			case 3:
				btyFlag |= 1;
				break;
			case 7:
				btyFlag |= 1;
				break;
			case 15:
				btyFlag |= 1;
				break;
			case 9:
				btyFlag |= 1;
				break;
			case 16:
				btyFlag |= 1;
				break;
			case 17:
				btyFlag |= 1;
				break;
			case 18:
				btyFlag |= 1;
			case 5:
			case 6:
			case 8:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
			}
		}
		if (btyFlag != 0)
			this.admin.setBtyFlag(btyFlag);
	}

	public List<TPCardSysConnectNum> getConnNum() {
		return this.connNum;
	}

	public void setConnNum(List<TPCardSysConnectNum> connNum) {
		this.connNum = connNum;
	}

	public static final class FieldID {
		public static final int FID_TP_ADMIN = 1;
		public static final int FID_TP_TEXTOBJ = 2;
		public static final int FID_TP_BLOB = 4;
		public static final int FID_TP_HITLOG = 5;
		public static final int FID_FGP_MAIN = 6;
		public static final int FID_UPDATE_INFO = 7;
		public static final int FID_CONNNUM_LIST = 8;
	}
}