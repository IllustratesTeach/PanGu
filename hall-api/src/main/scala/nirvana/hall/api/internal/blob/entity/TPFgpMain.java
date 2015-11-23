package nirvana.hall.api.internal.blob.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class TPFgpMain
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  private long id;

  private short dbid;

  private long tpCardId;

  private byte bty;

  private short fgp;

  private byte viewId;

  private Short objectType;

  private String createUser;

  private Timestamp createTime;

  private String updateUser;

  private Timestamp updateTime;

  private Long rcn;

  private String deviceId;

  private Short deviceIdType;

  private Integer deviceProduerId;

  private String deviceModelNo;

  private Integer deviceType;

  private String createrSiteId;

  private String orgScanner;

  private String orgScannerUnitCode;

  private Timestamp orgScanTime;

  private Short orgAfisVendorId;

  private String comments;

  private List<TPBlobData> blobData;

  public long getId()
  {
    return this.id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public long getTpCardId() {
    return this.tpCardId;
  }

  public void setTpCardId(long tpCardId) {
    this.tpCardId = tpCardId;
  }

  public byte getBty() {
    return this.bty;
  }

  public void setBty(byte bty) {
    this.bty = bty;
  }

  public short getFgp() {
    return this.fgp;
  }

  public void setFgp(short fgp) {
    this.fgp = fgp;
  }

  public byte getViewId() {
    return this.viewId;
  }

  public void setViewId(byte viewId) {
    this.viewId = viewId;
  }

  public Short getObjectType() {
    return this.objectType;
  }

  public void setObjectType(Short objectType) {
    this.objectType = objectType;
  }

  public String getCreateUser() {
    return this.createUser;
  }

  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }

  public Timestamp getCreateTime() {
    return this.createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public String getUpdateUser() {
    return this.updateUser;
  }

  public void setUpdateUser(String updateUser) {
    this.updateUser = updateUser;
  }

  public Timestamp getUpdateTime() {
    return this.updateTime;
  }

  public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
  }

  public Long getRcn() {
    return this.rcn;
  }

  public void setRcn(Long rcn) {
    this.rcn = rcn;
  }

  public String getDeviceId() {
    return this.deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public Short getDeviceIdType() {
    return this.deviceIdType;
  }

  public void setDeviceIdType(Short deviceIdType) {
    this.deviceIdType = deviceIdType;
  }

  public Integer getDeviceProduerId() {
    return this.deviceProduerId;
  }

  public void setDeviceProduerId(Integer deviceProduerId) {
    this.deviceProduerId = deviceProduerId;
  }

  public String getDeviceModelNo() {
    return this.deviceModelNo;
  }

  public void setDeviceModelNo(String deviceModelNo) {
    this.deviceModelNo = deviceModelNo;
  }

  public Integer getDeviceType() {
    return this.deviceType;
  }

  public void setDeviceType(Integer deviceType) {
    this.deviceType = deviceType;
  }

  public String getCreaterSiteId() {
    return this.createrSiteId;
  }

  public void setCreaterSiteId(String createrSiteId) {
    this.createrSiteId = createrSiteId;
  }

  public String getOrgScanner() {
    return this.orgScanner;
  }

  public void setOrgScanner(String orgScanner) {
    this.orgScanner = orgScanner;
  }

  public String getOrgScannerUnitCode() {
    return this.orgScannerUnitCode;
  }

  public void setOrgScannerUnitCode(String orgScannerUnitCode) {
    this.orgScannerUnitCode = orgScannerUnitCode;
  }

  public Timestamp getOrgScanTime() {
    return this.orgScanTime;
  }

  public void setOrgScanTime(Timestamp orgScanTime) {
    this.orgScanTime = orgScanTime;
  }

  public Short getOrgAfisVendorId() {
    return this.orgAfisVendorId;
  }

  public void setOrgAfisVendorId(Short orgAfisVendorId) {
    this.orgAfisVendorId = orgAfisVendorId;
  }

  public void setDbid(short dbid)
  {
    this.dbid = dbid;
  }

  public short getDbid()
  {
    return this.dbid;
  }

  public void setBlobData(List<TPBlobData> blobData)
  {
    this.blobData = blobData;
  }

  public List<TPBlobData> getBlobData()
  {
    return this.blobData;
  }

  public boolean hasBlob(long blobId)
  {
    return false;
  }

  public boolean hasBlob()
  {
    return true;
  }

  public void setCreateInfo(OpInfo opInfo)
  {
    if (opInfo == null) return;
    this.createUser = opInfo.curUser;
    this.createTime = opInfo.curTime;
  }

  public void setUpdateInfo(OpInfo opInfo)
  {
    if (opInfo == null) return;
    this.updateUser = opInfo.curUser;
    this.updateTime = opInfo.curTime;
  }

  public void setComments(String comments)
  {
    this.comments = comments;
  }

  public String getComments()
  {
    return this.comments;
  }

  public static final class FieldID
  {
    public static final int FID_SID = 1;
    public static final int FID_RCN = 2;
    public static final int FID_ID = 3;
    public static final int FID_CRTINFO = 4;
    public static final int FID_UPDINFO = 5;
    public static final int FID_DEVICEID = 6;
    public static final int FID_DEVICEPRODUCERID = 7;
    public static final int FID_DEVICETYPEID = 8;
    public static final int FID_DEVICETYPE = 9;
    public static final int FID_OBJECTTYPE = 10;
    public static final int FID_DEVICEMODELNO = 11;
    public static final int FID_CREATORSITEID = 12;
    public static final int FID_SIGNATURE = 13;
    public static final int FID_SIGALG = 14;
    public static final int FID_LOBDATA = 15;
    public static final int FID_LOBFMT = 16;
  }
}