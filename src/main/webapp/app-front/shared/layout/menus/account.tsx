import React from 'react';
import MenuItem from '../../../shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { NavLink as Link } from 'react-router-dom';
import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

const accountMenuItemsAuthenticated = (
  <>
    <MenuItem icon="wrench" to="/account/profil-main">
      <Translate contentKey="global.menu.account.settings">Settings</Translate>
    </MenuItem>
    <MenuItem icon="sign-out-alt" to="/logout">
      <Translate contentKey="global.menu.account.logout">Sign out</Translate>
    </MenuItem>
  </>
);

const accountMenuItems = (
  <>
    <div className="menuAcount">
      <MenuItem id="login-item" icon="sign-in-alt" to="/login">
        <Translate contentKey="global.menu.account.login">Sign in</Translate>
      </MenuItem>
      <MenuItem icon="sign-in-alt" to="/account/register">
        <Translate contentKey="global.menu.account.register">Register</Translate>
      </MenuItem>
    </div>
  </>
);

export const AccountMenu = ({ isAuthenticated = false }) => (
  <NavDropdown icon="user-circle" id="account-menu">
    {isAuthenticated ? accountMenuItemsAuthenticated : accountMenuItems}
  </NavDropdown>
);

export default AccountMenu;
