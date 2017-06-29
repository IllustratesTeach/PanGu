package nirvana.hall.v70.internal.blob.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TextObject implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long rcn;
	private List<Object> entityList = new ArrayList();

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRcn() {
		return this.rcn;
	}

	public void setRcn(Long rcn) {
		this.rcn = rcn;
	}

	public void setEntityList(List<Object> entityList) {
		this.entityList = entityList;
	}

	public List<Object> getEntityList() {
		return this.entityList;
	}

	public <T> T getEntity(Class<T> klass) {
		for (Iterator localIterator = this.entityList.iterator(); localIterator
				.hasNext();) {
			Object obj = localIterator.next();

			if (obj.getClass() == klass) {
				return (T) obj;
			}
		}
		return null;
	}
}