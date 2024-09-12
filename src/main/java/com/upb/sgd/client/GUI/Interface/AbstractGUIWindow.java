/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.upb.sgd.client.GUI.Interface;

import com.upb.sgd.client.Mediator.ClientMediator;

/**
 *
 * @author sebastian
 */
public abstract class AbstractGUIWindow implements GUIWindow{
    protected ClientMediator mediator;

    public AbstractGUIWindow(ClientMediator mediator){
        this.mediator = mediator;
    }

    @Override
    public abstract void Init();

    @Override
    public abstract void Close();
}
