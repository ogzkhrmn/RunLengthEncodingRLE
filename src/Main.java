import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\ogzkh\\Pictures\\lena\\lena_color.gif");

        BufferedImage image = null;

        try {
            BufferedImage img = ImageIO.read(file);
            int width = img.getWidth();
            int height = img.getHeight();
            int[][] imgArr = new int[width][height];
            Raster raster = img.getData();
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    imgArr[i][j] = raster.getSample(i, j, 0) ;
                }
            }
            printRLE_rowRow(imgArr, height, width);
            printRLE_ColumnColumn(imgArr, height, width);
            printRLE_zigZag(imgArr, height, width);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printRLE_rowRow(int[][] pixels, int height, int width) {
        ArrayList<Integer> rleList = new ArrayList<>();
        int size = 0;
        int item = 0;
        int j = 0;

        for (int i = 0; i < height; i++) {
            if (i % 2 == 0) {
                j = 0;
                while (j < width) {
                    if (pixels[i][j] == item) {
                        size++;
                        j++;
                    } else {
                        addList(rleList, size, item);
                        item = pixels[i][j];
                        size = 0;
                    }
                }
            } else {
                j = width - 1;
                while (j >= 0) {
                    if (pixels[i][j] == item) {
                        size++;
                        j--;
                    } else {
                        addList(rleList, size, item);
                        item = pixels[i][j];
                        size = 0;
                    }
                }
            }
        }
        if (size != 0) {
            rleList.add(size);
            rleList.add(item);
        }
        System.out.println((1 - (double) rleList.size() / (double) (height * width)) * 100);
    }

    private static void addList(ArrayList<Integer> rleList, int size, int item) {
        if(size > 2){
            rleList.add(size);
            rleList.add(item);
        }else{
            for (int range = 0; range < size; range++){
                rleList.add(item);
            }
        }
    }

    public static void printRLE_ColumnColumn(int[][] pixels, int height, int width) {
        ArrayList<Integer> rleList = new ArrayList<>();
        int size = 1;
        int item = 0;
        int j = 0;

        for (int i = 0; i < height; i++) {
            if (i % 2 == 0) {
                j = 0;
                while (j < width) {
                    if (pixels[j][i] == item) {
                        size++;
                        j++;
                    } else {
                        addList(rleList, size, item);
                        item = pixels[j][i];
                        size = 0;
                    }
                }
            } else {
                j = width - 1;
                while (j >= 0) {
                    if (pixels[j][i] == item) {
                        size++;
                        j--;
                    } else {
                        addList(rleList, size, item);
                        item = pixels[j][i];
                        size = 0;
                    }
                }
            }
        }
        if (size != 0) {
            rleList.add(size);
            rleList.add(item);
        }
        System.out.println((1 - (double) rleList.size() / (double) (height * width)) * 100);
    }

    static void printRLE_zigZag(int[][] pixels, int n, int m) {
        ArrayList<Integer> rleList = new ArrayList<>();
        int size = 0;
        int item = 0;
        for (int i = 0; i < n + m - 1; i++) {
            if (i % 2 == 1) {
                // down left
                int x = i < n ? 0 : i - n + 1;
                int y = i < n ? i : n - 1;
                while (x < m && y >= 0) {
                    if (pixels[x][y] == item) {
                        size++;
                    } else {
                        addList(rleList, size, item);
                        item = pixels[x][y];
                        size = 1;
                    }
                    x++;
                    y--;
                }
            } else {
                // up right
                int x = i < m ? i : m - 1;
                int y = i < m ? 0 : i - m + 1;
                while (x >= 0 && y < n) {
                    if (pixels[x][y] == item) {
                        size++;
                    } else {
                        addList(rleList, size, item);
                        item = pixels[x][y];
                        size = 1;
                    }
                    x--;
                    y++;
                }
            }
        }
        if (size != 0) {
            rleList.add(size);
            rleList.add(item);
        }
        System.out.println((1 - (double) rleList.size() / (double) (m * n)) * 100);
    }
}
