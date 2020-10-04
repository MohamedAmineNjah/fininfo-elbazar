import { lightGreen, blue, red } from '@material-ui/core/colors';
import { makeStyles, createStyles, Theme } from '@material-ui/core/styles';

export const useStyles = makeStyles(theme => ({
  formControl: {
    margin: theme.spacing(1),
    minWidth: 120,
  },
  root: {
    '& > *': {
      margin: theme.spacing(1),
    },
  },
  selectEmpty: {
    marginTop: theme.spacing(2),
  },
  input: {
    display: 'none',
  },
  textField: {
    width: 180,
  },
  button: {
    margin: theme.spacing(1),
  },
  media: {
    height: 0,
    paddingTop: '0%', // 16:9
  },
  avatar: {
    backgroundColor: red[500],
  },
  expandOpen: {
    transform: 'rotate(180deg)',
  },
  expand: {
    transform: 'rotate(0deg)',
    marginLeft: 'auto',
    transition: theme.transitions.create('transform', {
      duration: theme.transitions.duration.shortest,
    }),
  },
  root2: {
    maxWidth: '100%',
    height: '100%',
    width: '50%',
    display: 'block',
    '&:hover': {
      background: '#c2bcc2',
    },
  },
  root3: {
    flexGrow: 1,
  },
  paper: {
    padding: theme.spacing(2),
    textAlign: 'center',
    color: theme.palette.text.secondary,
  },
  colorheader: {
    background: blue['800'],
  },
  coloractive: {
    color: lightGreen['600'],
  },
}));

export const useStylesClient = makeStyles((theme: Theme) =>
  createStyles({
    root: {
      ...theme.typography.h5,
      fontFamily: 'Open Sans',
      backgroundColor: theme.palette.background.paper,
      padding: theme.spacing(2),
    },
    button: {
      margin: theme.spacing(1),
    },
    image: {
      height: 100,
      width: 100,
      alignContent: 'center',
    },
    profilelogo: {
      height: 150,
      alignContent: 'center',
    },
    table: {
      minWidth: 650,
      padding: theme.spacing(5),
    },
    tablecontainer: {
      padding: theme.spacing(5),
    },
  })
);
