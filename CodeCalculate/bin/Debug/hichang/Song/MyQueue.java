package hichang.Song;
public class MyQueue {  
      
    /** 
     * ˫������ṹ 
     */  
    public class LinkNode {  
  
        // ������������  
        private Song date;  
  
        // ��¼��һ���ڵ�  
        private LinkNode prevLinkNode;  
  
        // ��¼��һ���ڵ�  
        private LinkNode nextLinkNode;  
  
        public LinkNode() {  
  
        }  
  
        public LinkNode(Song date, LinkNode prevLinkNode, LinkNode nextLinkNode) {  
            this.date = date;  
            this.prevLinkNode = prevLinkNode;  
            this.nextLinkNode = nextLinkNode;  
        }  
        
        public boolean equals(Song song){
     	   return (this.date.getSongID()==song.getSongID());
           }
    }  
      
    // ������  
    private int nodeSize;  
  
    // ͷ���  
    private LinkNode headNode;  
  
    // β�ͽڵ�  
    private LinkNode tailNode;  
      
    public MyQueue(){  
        headNode = null;  
        tailNode = null;  
    }  
  
    /**
     * ��ĳԪ�����ڶ��ж���
     */
    public boolean top(Song obj){
    	LinkNode link = this.findObject(obj);
    	if(link==headNode){
    		return true;
    	}
    	else if(link==tailNode){
    		tailNode.prevLinkNode.nextLinkNode = null;
    		link.prevLinkNode = null;
    		headNode.prevLinkNode = link;
    		link.nextLinkNode = headNode;
    		headNode = link;
    		return true;
    	}
    	else{
    		LinkNode temp = link.prevLinkNode;
        	temp.nextLinkNode = link.nextLinkNode;
        	link.nextLinkNode.prevLinkNode = temp;
        	link.prevLinkNode = null;
        	link.nextLinkNode = headNode;
        	headNode = link;
        	return true;
    	}
    	
    }
    
    /**
     * ɾ����ѡ��Ŀ
     */
    public boolean deleteSelected(Song obj){
    	LinkNode temp;
    	temp = this.findObject(obj);
    	if(nodeSize==1){
    		temp.date = null;
    		temp.prevLinkNode = null;
    		temp.nextLinkNode = null;
    		headNode = null;
    		tailNode = null;
    		nodeSize = 0;
    		return true;
    	}
    	else{
	    	if(temp==headNode){
	    		temp.nextLinkNode.prevLinkNode = null;
	    		headNode = temp.nextLinkNode;
	    		temp.date = null;
	    		temp.nextLinkNode = null;
	    		temp.prevLinkNode = null;
	    		nodeSize--;
	    		return true;
	    	}
	    	if(temp==tailNode){
	    		LinkNode temp1 = tailNode.prevLinkNode;
	    		temp1.nextLinkNode = null;
	    		tailNode.date = null;
	    		tailNode.prevLinkNode = null;
	    		tailNode = temp1;
	    		temp1.prevLinkNode.nextLinkNode = tailNode;
	    		nodeSize--;
	    		return true;
	    	}
	    	else{
	    		temp.prevLinkNode.nextLinkNode = temp.nextLinkNode;
	    		temp.nextLinkNode.prevLinkNode = temp.prevLinkNode;
	    		temp.prevLinkNode = null;
	    		temp.nextLinkNode = null;
	    		temp.date = null;
	    		nodeSize--;
	    		return true;
	    	}
    	}
    }
    
    /** 
     * ���Ԫ�� 
     */  
    public boolean add(Song element) {  
        if (nodeSize == 0) {  
            headNode = new LinkNode(element, null, tailNode);  
        }else {  
  
            if (tailNode == null) {  
                tailNode = new LinkNode(element, headNode, null);  
                headNode.nextLinkNode = tailNode;  
                nodeSize++;  
                return true;  
            }  
  
            LinkNode linkNode = tailNode;  
            tailNode = new LinkNode(element, linkNode, null);  
            linkNode.nextLinkNode = tailNode;  
        }  
        nodeSize++;  
        return true;  
    }  
      
    public Song poll() {  
          
        LinkNode headNodeTemp = headNode;  
        Song date = headNodeTemp.date;  
        if(headNode.nextLinkNode == null){  
            headNode.date = null;  
            headNode = null;  
            nodeSize--;  
            return date;  
        }else{  
            headNode = headNode.nextLinkNode;  
            if(headNode == tailNode){  
                tailNode = null;  
            }  
        }  
          
        nodeSize--;  
          
        return headNodeTemp.date;  
    }  
    
    /** 
     * �������Ԫ�� 
     */  
    public void clear() {  
    	
        LinkNode linkNodeNowTemp = headNode;  
        for (int i = 0; i < nodeSize; i++) {  
        	headNode = linkNodeNowTemp.nextLinkNode;
        	linkNodeNowTemp.prevLinkNode = null;
        	linkNodeNowTemp.date = null;
        	linkNodeNowTemp.nextLinkNode = null;
        }
        headNode = null;  
        tailNode = null;  
        nodeSize = 0;  
    }  

    /** 
     * �ж��Ƿ���� ,���ڷ���
     */  
    public LinkNode findObject(Song object) {  
  
        LinkNode linkNodeNowTemp = headNode;  
  
        for (int i = 0; i < nodeSize; i++) {  
  
            if (object.getSongID() == linkNodeNowTemp.date.getSongID()) {  
            	break;
            }  
  
            linkNodeNowTemp = linkNodeNowTemp.nextLinkNode;  
        }  
        return linkNodeNowTemp;     
    }  
      
    /** 
     * �����Ƿ�Ϊ�� 
     */  
    public boolean isEmpty() {  
        // TODO Auto-generated method stub  
        return nodeSize == 0;  
    }  
  
    public int size() {  
        // TODO Auto-generated method stub  
        return nodeSize;  
    }  
      
    /** 
     * ���������Ų��ҽڵ� 
     *  
     * @param index 
     * @return 
     */  
    public LinkNode findLinkNodeByIndex(int index) {  
  
        LinkNode linkNodeNowTemp = headNode;  
  
        for (int i = 0; i < nodeSize; i++) {  
  
            if (i == index) {  
                return linkNodeNowTemp;  
            }  
  
            linkNodeNowTemp = linkNodeNowTemp.nextLinkNode;  
        }  
        return null;  
    }  
      
    @Override  
    public String toString() {  
  
        StringBuffer str = new StringBuffer("[");  
        LinkNode linkNode = null;  
        for (int i = 0; i < nodeSize; i++) {  
  
            linkNode = findLinkNodeByIndex(i);  
  
            str.append("[" + linkNode.date + "],");  
  
        }  
  
        if (nodeSize > 0) {  
            return str.substring(0, str.lastIndexOf(",")) + "]";  
        }  
  
        return str.append("]").toString();  
    }
    


      
}  
