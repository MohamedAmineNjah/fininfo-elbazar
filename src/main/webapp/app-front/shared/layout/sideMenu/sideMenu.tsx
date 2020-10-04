import './sideMenu.scss';

import React, { useState, useEffect, useLayoutEffect } from 'react';
import { Translate, Storage } from 'react-jhipster';
import { Navbar,NavLink, NavItem, Nav, NavbarToggler, NavbarBrand, Collapse } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { LinkContainer } from 'react-router-bootstrap';
import LoadingBar from 'react-redux-loading-bar';
import Cookie from 'js-cookie';
import {getEntities,setNameSubCat} from '../../../modules/products/products.reducer';
import { IRootState } from '../../../shared/reducers';
import { switchSideNav } from '../../../shared/reducers/category.reducer';

import { connect } from 'react-redux';

export interface ISideMenuProps extends StateProps, DispatchProps  {
  isAuthenticated: boolean;
  isAdmin: boolean;
  switchnav:boolean
}

export const SubCategoryItem  = (props: any) => {


  const handleSubCategory=(event)=>{
    Cookie.set("nomCat",props.category.nom)
    Cookie.set("nomSubCat",props.subCategory.nom)
    Cookie.set("idSubCat",props.subCategory.id)
    props.setsubcat(props.subCategory.nom);
    window.scrollTo(0,0);
    if(props.size<576){
      props.switchsidenav(true);
    }
      props.getprod(props.subCategory.id,0, 8,'id','asc');
  }
  return(
    <LinkContainer to={`/products/${props.subCategory.id}`}>
      <NavItem onClick={handleSubCategory} className="sub-cat-item" >{props.subCategory.nom}</NavItem>  
    </LinkContainer>
 
  )
}
export const CategoryItem  = (props: any) => {
  const [open, setOpen] = useState(false);

  const handleCategory=(event)=>{
    
    const navItems= document.getElementsByClassName("iconSideMenuRotate")

    if(event.currentTarget.querySelector("svg").classList.contains("iconSideMenuRotate")){
      event.currentTarget.querySelector("svg").classList.remove("iconSideMenuRotate");
    }else{
      event.currentTarget.querySelector("svg").classList.toggle("iconSideMenuRotate");
    }
    
    setOpen(!open);
  }
  const items=[];
  if(props.category.sous_categorie){
    for (let i = 0; i < props.category.sous_categorie.length; i++) {
      items.push(
        <SubCategoryItem
        getprod={props.getprod}
        size={props.size}
        setsubcat={props.setCat}
        switchsidenav={props.switchsidenav}
        key={props.category.sous_categorie[i].id}
        subCategory={props.category.sous_categorie[i]}
        category={props.category}
        />
      )
    
    }
  }
  return(
    <div>
        <NavItem onClick={handleCategory} className={props.class}  aria-expanded={!open}> {props.category.nom} <FontAwesomeIcon className="iconSideMenu" icon="angle-right"/></NavItem>
        <Collapse className="sub-cat-div" isOpen={!open} >
          {items}
        </Collapse>
        </div>
  )
}
const SideMenu = (props: ISideMenuProps) => {
const [categoryList, setCategoryList] = useState([]);
const [size, setSize] = useState(0);
  useEffect(() => {
    setCategoryList(props.listCategories);
  }, [props.listCategories]);
  useLayoutEffect(() => {
    function updateSize() {
      setSize(window.innerWidth);
      if(window.innerWidth<576){
        props.switchSideNav(true);
      }
    }
    window.addEventListener('resize', updateSize);
    updateSize();
    return () => window.removeEventListener('resize', updateSize);
  }, []);
    const items=[];
    if(categoryList){
      for (let i = 0; i < categoryList.length; i++) {

        items.push(
          <CategoryItem
          getprod={props.getEntities}
          setCat={props.setNameSubCat}
          switchsidenav={props.switchSideNav}
          size={size}
          key={categoryList[i].id}
          class="cat-item"
          category={categoryList[i]}
          />
        )
      
      }
    }
    

  return (
    <div id="app-sideMenu"  className={props.switchnav? "showSid":"hideNav"}>
      <Navbar  expand="sm"  className=" accordion__section">
        {items}
      </Navbar>
    </div>
  );
};

const mapStateToProps = ({ categories }: IRootState) => ({
  listCategories : categories.listCategories,
  switchNav: categories.showedSidNav,
});

const mapDispatchToProps = { getEntities,
  setNameSubCat,
  switchSideNav
};
type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SideMenu);