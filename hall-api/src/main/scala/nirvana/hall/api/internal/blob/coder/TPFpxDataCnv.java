package nirvana.hall.api.internal.blob.coder;

import java.util.Map;

import nirvana.hall.api.internal.blob.entity.TPFPXData;

public class TPFpxDataCnv
{
  public static TPFPXData toEntity(TPFpxDataObject obj)
  {
    TPFPXData ent = new TPFPXData();
    Map list = obj.text.itemList;

    ent.setId(obj.id);
    Object temp;
    if ((temp = list.get(Integer.valueOf(81))) != null) ent.setApprovedby(TextEntityObject.vgetString(temp));
    if ((temp = list.get(Integer.valueOf(74))) != null) ent.setAssoCaseId(TextEntityObject.vgetString(temp));
    if ((temp = list.get(Integer.valueOf(73))) != null) ent.setAssoPersonId(TextEntityObject.vgetString(temp));
    if ((temp = list.get(Integer.valueOf(50))) != null) ent.setComments(TextEntityObject.vgetString(temp));
    if ((temp = list.get(Integer.valueOf(82))) != null) ent.setFpxFlag(TextEntityObject.vgetString(temp));
    if ((temp = list.get(Integer.valueOf(70))) != null) ent.setFpxPriority(TextEntityObject.vgetString(temp));
    if ((temp = list.get(Integer.valueOf(72))) != null) ent.setFpxPurpose(TextEntityObject.vgetString(temp));
    if ((temp = list.get(Integer.valueOf(75))) != null) ent.setFpxTimeLimit(TextEntityObject.vgetString(temp));
    if ((temp = list.get(Integer.valueOf(76))) != null) ent.setFpxUnitCode(TextEntityObject.vgetString(temp));
    if ((temp = list.get(Integer.valueOf(77))) != null) ent.setFpxUnitName(TextEntityObject.vgetString(temp));
    if ((temp = list.get(Integer.valueOf(79))) != null) ent.setLinkMan(TextEntityObject.vgetString(temp));
    if ((temp = list.get(Integer.valueOf(80))) != null) ent.setLinkPhone(TextEntityObject.vgetString(temp));

    return ent;
  }

  public static void add(Map<Integer, Object> list, int key, Object value)
  {
    if (value != null) list.put(Integer.valueOf(key), value);
  }

  public static TPFpxDataObject toObject(TPFPXData ent)
  {
    TPFpxDataObject obj = new TPFpxDataObject();

    obj.id = ent.getId();
    ent.setId(obj.id);
    ent.setPremium(obj.premium);
    ent.setFpxDateTime(obj.fpxDateTime);

    Map list = obj.text.itemList;

    add(list, 81, ent.getApprovedby());
    add(list, 74, ent.getAssoCaseId());
    add(list, 73, ent.getAssoPersonId());
    add(list, 50, ent.getComments());
    add(list, 82, ent.getFpxFlag());
    add(list, 70, ent.getFpxPriority());
    add(list, 72, ent.getFpxPurpose());
    add(list, 75, ent.getFpxTimeLimit());
    add(list, 76, ent.getFpxUnitCode());
    add(list, 77, ent.getFpxUnitName());
    add(list, 79, ent.getLinkMan());
    add(list, 80, ent.getLinkPhone());
    return obj;
  }
}