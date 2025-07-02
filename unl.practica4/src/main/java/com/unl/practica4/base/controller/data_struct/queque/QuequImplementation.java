package com.unl.practica4.base.controller.data_struct.queque;

import com.unl.practica4.base.controller.data_struct.list.LinkedList;

public class QuequImplementation<E> extends LinkedList<E> {
    private Integer top;

    public Integer getTop() {
        return this.top;
    }

    public QuequImplementation(Integer top){
        this.top = top;
    }

    protected Boolean isFullQueque() {
        return this.top >= getLength();
    }

    protected void queue(E info) throws Exception {
        if(!isFullQueque()) {
            add(info);
        } else {
            throw new ArrayIndexOutOfBoundsException("Queque full");
        }
    }
    protected E dequeue() throws Exception {       
        return deleteFirst();
        
    }
}
