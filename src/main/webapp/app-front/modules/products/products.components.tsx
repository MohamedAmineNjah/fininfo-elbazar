import React, { useState } from 'react';
import { makeStyles, createStyles, Theme, CardActionArea, Snackbar } from '@material-ui/core';
import { Link, RouteComponentProps } from 'react-router-dom';
import clsx from 'clsx';
import {
  Card, CardImg, CardText, CardBody,
  CardTitle, CardSubtitle, Button
} from 'reactstrap';
import CardHeader from '@material-ui/core/CardHeader';
import CardMedia from '@material-ui/core/CardMedia';
import CardContent from '@material-ui/core/CardContent';
import CardActions from '@material-ui/core/CardActions';
import Collapse from '@material-ui/core/Collapse';
import Avatar from '@material-ui/core/Avatar';
import IconButton from '@material-ui/core/IconButton';
import Typography from '@material-ui/core/Typography';
import { red, green, lime, lightGreen } from '@material-ui/core/colors';
import FavoriteIcon from '@material-ui/icons/Favorite';
import ShareIcon from '@material-ui/icons/Share';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart';
import { height } from '@fortawesome/free-solid-svg-icons/faAngleRight';
import EllipsisText from 'react-ellipsis-text';
import Rating from '@material-ui/lab/Rating';
import Badge from '@material-ui/core/Badge';
import { divide } from 'lodash';
import MuiAlert from '@material-ui/lab/Alert';
const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    icone: {
      color: 'white',
      marginTop: '-5px',
    },
    media: {
      padding: '10%',
      width: '200px',
      height: '220px',
      margin: 'auto',
      cursor: 'pointer',
    },
    cardHeader: {
      backgroundColor: green['700'],
      color: 'white',
      height: '50px',
    },
    cardHeaderRed: {
      backgroundColor: red['600'],
      color: 'white',
      height: '50px',
    },
    badge: {
      position: 'relative',
      top: '100px',
      left: '-18%',
    },
    cardContent: {
      padding: '10px 10px 0px 10px',
    },

    cardContentItem: {
      fontWeight: 600,
      cursor: 'pointer',
    },
    cardContentItem2: {
      color: red[500],
      fontWeight: 700,
    },
    rating: {
      float: 'left',
      display: 'block',
      paddingTop: '0px',
    },
    expand: {
      transform: 'rotate(0deg)',
      marginLeft: 'auto',
      transition: theme.transitions.create('transform', {
        duration: theme.transitions.duration.shortest,
      }),
    },
    expandOpen: {
      transform: 'rotate(180deg)',
    },
    avatar: {
      backgroundColor: red[500],
    },
  })
);

export const ProductItem = props => {
    const classes = useStyles();
    const [expanded, setExpanded] = React.useState(false);
  
    const showDetail = () => {
    
    };
    const addToCart = () => {
        const itemPanier= props.listprodpanier.filter(item=>item.id===props.product.id);
        if((itemPanier[0] && itemPanier[0].qte<itemPanier[0].quantite_vente_max) || !itemPanier[0]) {
          props.addFunction(props.product,1);
            props.setopensnack(true);
            
        }else{
          props.setopensnackerror(true);
        }
      };
      const IsHorsStock = horsStock => {
        if (!horsStock) {
          return (<React.Fragment>
              <CardTitle style={{'display':"flex"}}><span className="prodDisponible">Disponible</span>  <IconButton onClick={addToCart} style={{"float":"right"}} aria-label="settings">
              <ShoppingCartIcon className="productCart" />
            </IconButton>
            </CardTitle>
          </React.Fragment>)
        }else{
          return (<React.Fragment>
             <CardTitle style={{'display':"flex"}}>
               <span className="prodIndisponible">Indisponible</span> 
            </CardTitle>
         </React.Fragment>)
        }
      
      };
      const IsPromo = product => {
        const currentDate= new Date();
        const startDate= new Date(product.debut_promo);
        const endDate= new Date(product.fin_promo);
        currentDate.setHours(0,0,0,0)
        startDate.setHours(0,0,0,0)
        endDate.setHours(0,0,0,0)
        
        const isInPromo = (startDate.getTime() <= currentDate.getTime())&&(currentDate.getTime()<=endDate.getTime())&& product.eligible_Remise===true && product.remise>0
        console.log(isInPromo)
        const prix= props.product.prix_ttc-((props.product.prix_ttc*props.product.remise)/100);
        if (isInPromo) {
          return (
            <CardSubtitle className="productPrix">
              <span style={{'textDecoration':'line-through','color':'#999'}}>{props.product.prix_ttc + " DT"} </span> <span style={{'marginLeft':'2%'}}>{prix.toFixed(3)+" Dt"} </span>
            </CardSubtitle>
          );
        } else {
          return (
            <CardSubtitle className="productPrix">
                {props.product.prix_ttc.toFixed(3) + " DT"} 
              
              </CardSubtitle>
          );
        }
      };
  const IsPromoBadge = product => {
    const currentDate = new Date();
    const startDate = new Date(product.debut_promo);
    const endDate = new Date(product.fin_promo);
    currentDate.setHours(0,0,0,0)
        startDate.setHours(0,0,0,0)
        endDate.setHours(0,0,0,0)
    const isInPromo = startDate.getTime() <= currentDate.getTime() && currentDate.getTime() <= endDate.getTime() && product.eligible_Remise===true && product.remise>0;
    if (isInPromo) {
      return <Badge badgeContent={'promo'} color="error" className={classes.badge}></Badge>;
    } else {
      return <div></div>;
    }
  };
  return (
    <Card className="productCard"  style={{borderRadius: "20px"}}>
      {IsHorsStock(props.product.hors_stock)}
      <Link to={`/product/${props.product.id}`}>
        <CardImg onClick={showDetail} top width="100%" src={"media/"+ props.product.image_url} className="imgProduct" alt="Card image cap" />
       </Link>
        {IsPromoBadge(props.product)}
        <CardBody onClick={showDetail}>
        <Link to={`/product/${props.product.id}`}>
          <CardTitle className="productName"><EllipsisText text={props.product.nom} length={29} /></CardTitle>
        </Link>
          {IsPromo(props.product)}
          <CardText><Rating name="pristine" value={3} /></CardText>
        </CardBody>
      </Card>
  );
};
function Alert(props) {
  return <MuiAlert elevation={6} variant="filled" {...props} />;
}
export const SnackBarCustom = (props: any) => {
  return (
    <div>
      <Snackbar
        anchorOrigin={{
          vertical: 'top',
          horizontal: 'left',
        }}
        open={props.isSnackOpen}
        autoHideDuration={2000}
        onClose={props.handleClose}
        action={
          <React.Fragment>
            <Button color="secondary" size="small" onClick={props.handleClose}>
              UNDO
            </Button>
          </React.Fragment>
        }
      >
     
        <Alert severity={props.type}>{props.textMessage}</Alert>
      </Snackbar>
    </div>
  );
};

