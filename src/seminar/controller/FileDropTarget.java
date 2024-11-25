package seminar.controller;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;

import javax.swing.JOptionPane;
import seminar.model.FileTransfer;
import seminar.view.component.screen.Screen;
import seminar.view.component.screen.basic.BasicScreen;

public class FileDropTarget extends DropTargetAdapter {
	private FileTransfer model;
	private Screen view;

	public FileDropTarget(Screen view, FileTransfer model) {
		this.view = view;
		this.model = model;
	}

	@Override
	public void drop(DropTargetDropEvent event) {
		try {
			// Kiểm tra xem dữ liệu có phải là file không
			if (event.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				event.acceptDrop(DnDConstants.ACTION_COPY);
				List<File> files = (List<File>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

				// Chỉ xử lý nếu số file được kéo là 1
				if (files.size() == 1) {
					File file = files.get(0);
					String fileName = file.getName().toLowerCase();

					// Kiểm tra định dạng file
					String src = file.getAbsolutePath();
					String des = file.getParent();

					model.setSrc(src);
					model.setDes(des);

					JOptionPane.showMessageDialog(view, "Đã nhận file: " + src, "Thành công",
							JOptionPane.PLAIN_MESSAGE);
					view.getTaEncrypt().setText("Đã nhận file: " + src);
				}
			} else {
				event.rejectDrop();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
