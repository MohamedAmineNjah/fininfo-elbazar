import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { NavLink as Link } from 'react-router-dom';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

const accountMenuItemsAuthenticated = (isAdministrator) => (
  <>
    <MenuItem icon="wrench" to="/account/settings">
      <Translate contentKey="global.menu.account.settings">Settings</Translate>
    </MenuItem>
    <MenuItem icon="lock" to="/account/password">
      <Translate contentKey="global.menu.account.password">Password</Translate>
    </MenuItem>
    {isAdministrator ? 
    <MenuItem icon="sign-in-alt" to="/account/register">
      <Translate contentKey="global.menu.account.register">Register</Translate>
    </MenuItem> : null }
    <MenuItem icon="sign-out-alt" to="/logout">
      <Translate contentKey="global.menu.account.logout">Sign out</Translate>
    </MenuItem>
  </>
);

const accountMenuItems = (
  <>
    <MenuItem id="login-item" icon="sign-in-alt" to="/login">
      <Translate contentKey="global.menu.account.login">Sign in</Translate>
    </MenuItem>
  
  </>
);

export const AccountMenu = ({ isAuthenticated = false, isAdmin = false }) => (
  <NavDropdown icon="user" name={translate('global.menu.account.main')} id="account-menu">
    {isAuthenticated ? accountMenuItemsAuthenticated(isAdmin) : accountMenuItems}
  </NavDropdown>
);

export default AccountMenu;
