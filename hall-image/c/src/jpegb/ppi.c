/*******************************************************************************

License: 
This software was developed at the National Institute of Standards and 
Technology (NIST) by employees of the Federal Government in the course 
of their official duties. Pursuant to title 17 Section 105 of the 
United States Code, this software is not subject to copyright protection 
and is in the public domain. NIST assumes no responsibility  whatsoever for 
its use by other parties, and makes no guarantees, expressed or implied, 
about its quality, reliability, or any other characteristic. 

Disclaimer: 
This software was developed to promote biometric standards and biometric
technology testing for the Federal Government in accordance with the USA
PATRIOT Act and the Enhanced Border Security and Visa Entry Reform Act.
Specific hardware and software products identified in this software were used
in order to perform the software development.  In no case does such
identification imply recommendation or endorsement by the National Institute
of Standards and Technology, nor does it imply that the products and equipment
identified are necessarily the best available for the purpose.  

*******************************************************************************/

/***********************************************************************
      LIBRARY: JPEGB - Baseline (Lossy) JPEG Utilities

      FILE:    PPI.C
      AUTHORS: Michael Garris
               Craig Watson
      DATE:    01/09/2001

      Contains routines responsible for determining a compressed image's
      scan resolution in units of pixels per inch.

      ROUTINES:
#cat: get_ppi_jpegb - If possible, computes and returns the scan resolution
#cat:                 in pixels per inch of a JPEGB datastream given a
#cat:                 JPEGB decompess structure.

***********************************************************************/

#include <stdio.h>
#include "../include/jpegb.h"

#define CM_PER_INCH   2.54

/************************************************************************/
int get_ppi_jpegb(int *oppi, j_decompress_ptr cinfo)
{
   int ppi;

   /* Get and set scan density in pixels per inch. */
   switch(cinfo->density_unit){
      /* pixels per inch */
      case 1:
         /* take the horizontal pixel density, even if the vertical is */
         /* not the same */
         ppi = cinfo->X_density;
         break;
      /* pixels per cm */
      case 2:
         /* compute ppi from horizontal density even if not */
         /* equal to vertical */
         ppi = (int)((cinfo->X_density * CM_PER_INCH) + 0.5);
         break;
      /* unknown density */
      case 0:
         /* set ppi to -1 == UNKNOWN */
         //ppi = -1;
		  ppi = 0;
         break;
      /* ERROR */
      default:
         fprintf(stderr, "ERROR : get_ppi_jpegb : ");
         fprintf(stderr, "illegal density unit = %d\n", cinfo->density_unit);
         return(-2);
   }

   *oppi = ppi;

   return(0);
}
