import { container } from "assets/jss/material-kit-react.jsx";

const componentsStyle = {
  section: {
    padding: "70px 0"
  },
  marginAuto: {
    marginLeft: "auto !important",
    marginRight: "auto !important",
    marginBottom: "100px"
  },
  container,
  brand: {
    color: "#FFFFFF",
    textAlign: "left"
  },
  title: {
    fontSize: "4.2rem",
    fontWeight: "600",
    display: "inline-block",
    position: "relative"
  },
  subtitle: {
    fontSize: "1.313rem",
    maxWidth: "500px",
    margin: "10px 0 0"
  },
  main: {
    background: "#FFFFFF",
    position: "relative",
    overflow: "hidden",
    marginTop: "-5rem",
    marginLeft: "-1rem",
    marginRight: "-1rem",
    width: "fit-content",
    borderRadius: "0",
    zIndex: "3",
    paddingBottom: "3rem"
  },
  mainRaised: {
    margin: "-3rem -1rem 0px",
    borderRadius: "6px",
    boxShadow:
      "0 16px 24px 2px rgba(0, 0, 0, 0.14), 0 6px 30px 5px rgba(0, 0, 0, 0.12), 0 8px 10px -5px rgba(0, 0, 0, 0.2)",
    overflow: "hidden"
  },
  mainContainer: {
    marginLeft: "-1rem",
    marginRight: "-1rem"
  },
  link: {
    textDecoration: "none"
  },
  textCenter: {
    textAlign: "center"
  }
};

export default componentsStyle;
