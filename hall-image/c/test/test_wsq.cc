#include <stdio.h>
#include <fstream>
#include "gtest/gtest.h"

#include "include/wsq.h"

using namespace std;

class WSQTest: public ::testing::Test {
protected:

    WSQTest() {
    }
    virtual ~WSQTest() {
    }
    virtual void SetUp() {
    }
    virtual void TearDown() {
    }

};

TEST_F(WSQTest, TestCrack) {
  ifstream file ("/Users/jcai/workspace/finger/nirvana-hall/hall-image/src/test/resources/wsq.data",
                 ios::in | ios::binary | ios::ate);
  ifstream::pos_type fileSize;
  char* fileContents;
  if(file.is_open()) {
    fileSize = file.tellg();
    fileContents = new char[fileSize];
    file.seekg(0, ios::beg);
    if (!file.read(fileContents, fileSize)) {
      cout << "fail to read" << endl;
    }
    file.close();
  }

  char* compressed_img_bin_data ="nirvan finger test";
  unsigned char* compressed_img_bin = (unsigned char*)fileContents;
  int compressed_size = 12;
  int width,height,ppi;
  int retval = wsq_decodedinfo(&width, &height, &ppi, compressed_img_bin, fileSize);
  int ndepth = 8;
  unsigned char* dest_img_bin = NULL;
  retval = wsq_decode_mem(&dest_img_bin, &width, &height, &ndepth, &ppi, NULL,
                          compressed_img_bin, fileSize);

  delete[] fileContents;
  free(dest_img_bin);
  ASSERT_EQ(0,retval);
  ASSERT_EQ(640,width);
  ASSERT_EQ(640,height);

}
