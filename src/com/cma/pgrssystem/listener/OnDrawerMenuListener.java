package com.cma.pgrssystem.listener;

import java.util.List;

import android.support.v4.widget.DrawerLayout;

import com.cma.pgrssystem.bean.DrawerMenuBean;

public interface OnDrawerMenuListener extends DrawerLayout.DrawerListener {

	public void handleDrawerMenuClick(DrawerMenuBean pMenuBean);

	public List<DrawerMenuBean> getDrawerMenuList();

}
