package iodesign.fileio.kbigfileread;

public class KReadFileThread extends Thread {

    private KReaderFileListener processPoiDataListeners;
    private String filePath;
    private long start;
    private long end;

    public KReadFileThread(KReaderFileListener processPoiDataListeners, long start, long end, String file) {
        this.setName(this.getName() + "-KReadFileThread");
        this.start = start;
        this.end = end;
        this.filePath = file;
        this.processPoiDataListeners = processPoiDataListeners;
    }

    @Override
    public void run() {
        KReadFile readFile = new KReadFile();
        readFile.setReaderListener(processPoiDataListeners);
        readFile.setEncode(processPoiDataListeners.getEncode());
//        readFile.addObserver();
        try {
            readFile.readFileByLine(filePath, start, end + 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}