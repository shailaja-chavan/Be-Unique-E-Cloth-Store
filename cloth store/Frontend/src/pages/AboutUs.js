

// const AboutUs = () => {
//   return (
// <div className="container mt-5">
// <div className="row">
//   <div className="col-md-6 offset-md-3">
//     <div className="bg-light p-4">
     
//       <p>
//       Online shopping for clothing allows consumers to buy apparel directly from sellers via the internet, offering convenience and access to a wide range of products. Shoppers can browse, select, and purchase items from the comfort of their homes. E-commerce, specifically in the clothing sector, enables businesses to showcase their collections, highlight sales, and present customer reviews, enhancing trust and credibility.
//       </p>
//       <ul className="list-unstyled">
//         <li>
//           <strong></strong><br />
//           The shift from traditional shopping to online platforms has revolutionized the industry by providing 24/7 access to products, eliminating geographical limitations, and offering personalized shopping experiences. E-commerce for clothing is essential for business growth, providing global reach, ease of operation, and increased visibility in the competitive market.

//         </li>
//         <li className="mt-3">
//           <strong></strong><br />
//           {/* The online shopping system is fast gaining media for to sale or purchase items from anywhere and anytime. It is basically based on Internet, It is related with B2C (Business to Customer) model and status of the design and development of e-commerce platform.E-business or Online business means business transactions that take place online with the help of the internet. The term e-business came into existence in the year 1996.  E-business is an abbreviation for SpareParts business. Therefore, the buyer and the seller do not meet personally. E-commerce is directly link to your business promotions, as it is the age of digital media. Making your business available online is crucial to your business development such as, highly convenience, wide exposure, global customer, easy to run, etc.  */}

//         </li>
//       </ul>
//     </div>
//   </div>
// </div>
// </div>
 
//   );
// };
// export default AboutUs;

import React from 'react';
import './CSS/AboutUs.css';
import aboutUsImage from '../images/aboutus.jpg';

const About = () => {
  return (
    <div className="about-container">
      <div className="about-image">
        <img src={aboutUsImage} alt="Clothing" />
      </div>
      <div className="about-text">
        <h1>About Beunique</h1>
        <p>
          Beunique is providing unique and high-quality products. 
          Our mission is to deliver exceptional value to our customers through our 
          innovative products and outstanding customer service.
        </p>
        <p>
          We believe in creating a unique shopping experience for our customers. 
          With a wide range of products, we aim to meet the diverse needs and preferences 
          of our valued customers.
        </p>
        <p>
          Thank you for choosing Beunique. We look forward to serving you and 
          helping you find the perfect products to suit your style and needs.
        </p>
      </div>
    </div>
  );
}

export default About;
