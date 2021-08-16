package com.example.uicaddressbook.Adapter;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uicaddressbook.Model.Pdf;
import com.example.uicnotifier.R;

import java.util.List;

public class PdfLoadAdapter extends RecyclerView.Adapter<PdfLoadAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<Pdf> pdfList;
    Context mContext;

    public PdfLoadAdapter(Context context, List<Pdf> pdfs){
        this.inflater = LayoutInflater.from(context);
        this.pdfList = pdfs;
        this.mContext = context;
    }

    @NonNull
    @Override
    public PdfLoadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.pdf_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PdfLoadAdapter.ViewHolder holder, int position) {
        String pdfUrl;
        Pdf pdf = pdfList.get(position);
        holder.pdfName.setText(pdf.getFileName());
        pdfUrl = pdf.getFileUrl();

        holder.pdfName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadFile(pdfUrl, pdf.getFileName());
            }
        });
    }

    private void downloadFile(String pdfUrl, String pdfTitle) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(pdfUrl));
        String tempTitle = pdfTitle.replace("","_");
        request.setTitle(tempTitle);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB){
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, tempTitle+".pdf");
        DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        request.setMimeType("application/pdf");
        request.allowScanningByMediaScanner();
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        downloadManager.enqueue(request);
    }

    @Override
    public int getItemCount() {
        return pdfList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView pdfName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pdfName = itemView.findViewById(R.id.tv_filename);
        }
    }
}
