import React from 'react';
import { Translate } from 'react-jhipster';
import { useState } from 'react';
import { NavItem, NavLink, NavbarBrand } from 'reactstrap';
import { NavLink as Link } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import appConfig from '../../../config/constants';
import { Badge, makeStyles } from '@material-ui/core';

const useStyles = makeStyles(theme => ({
  headerIcon: {
    fontSize: '20px',
  },
  customBadge: {
    backgroundColor: '#28a745',
    color: 'white',
    left: '6px',
  },
  customBadge2: {
    backgroundColor: '#d14952',
    color: 'white',
    left: '6px',
    bottom: '-37px'
  },
  ELBAZAR: {
    fontSize: '150%',

    marginLeft: '5%',
  },
  elbazarinfo: {
    fontSize: '14px',
    color: 'rgb(247, 58, 71)',
    marginLeft: '50px',
    top: '20px'
  }

}));
export const MenuIcone = props => (
  <div {...props} className="menu-icon">
    <FontAwesomeIcon icon="bars" />
  </div>
);
export const BrandIcon = props => (
  <div {...props} className="brand-icon">
    <img src="content/images/bazar_menthe.png" alt="Logo" />

  </div>
);

export const MenuI = props => (
  <div className="brand-logo">
    <MenuIcone onClick={props.handelsidenav2} />
    <NavbarBrand tag={Link} to="/" >
      <BrandIcon />
    </NavbarBrand>
  </div>
);

export const Brand = props => (
  <div className="brand-logo">

    <NavbarBrand tag={Link} to="/" >
      <BrandIcon onClick={props.handelsidenav} />
    </NavbarBrand>
  </div>
);

export const Home = props => {
  const classes = useStyles();
  return (
    <NavItem>
      <NavLink tag={Link} to="/" className="d-flex align-items-center">
        <FontAwesomeIcon icon="home" className={classes.headerIcon} onClick={props.handelsidenav} />
      </NavLink>
    </NavItem>
  );
};
export const Panier = props => {
  const classes = useStyles();
  return (
    <NavItem>
      <NavLink tag={Link} to="/panier" className="d-flex align-items-center">
        <FontAwesomeIcon icon="shopping-cart" className={classes.headerIcon} />

        <Badge
          badgeContent={props.nbProduit}
          className="badgePanier"
          classes={{ badge: classes.customBadge }}
          anchorOrigin={{ vertical: 'top', horizontal: 'right' }}
        ></Badge>
        <Badge
          max={99999999999999999}
          badgeContent={props.total}
          className="badgePanier"
          classes={{ badge: classes.customBadge2 }}
          anchorOrigin={{ vertical: 'bottom', horizontal: 'left' }}
        ></Badge>
      </NavLink>
    </NavItem>
  );
};

export const ELBAZAR = props => {
  const classes = useStyles();
  return (

    <NavbarBrand tag={Link} to="/">
      <div {...props} className="brand-bazar">
        <label className={classes.ELBAZAR} onClick={props.handelsidenav}><b>ELBAZAR</b></label>
      </div>
    </NavbarBrand>
  );
};


