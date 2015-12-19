#include "gtest/gtest.h"

#include "include/wsq.h"

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
  char* compressed_img_bin_data ="nirvan finger test";
  unsigned char* compressed_img_bin = (unsigned char*)compressed_img_bin_data;
  int compressed_size = 12;
  int width,height,ppi;
  int retval = wsq_decodedinfo(&width, &height, &ppi, compressed_img_bin, compressed_size);
}
