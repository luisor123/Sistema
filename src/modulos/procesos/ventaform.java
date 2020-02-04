/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulos.procesos;

import entidades.Cliente;
import entidades.TipoDocumento;
import entidades.Venta;
import entidades.VentaDetalle;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import modulos.sistema.menuprincipal.Conexion;
import servicios.ClienteService;
import servicios.TipoDocumentoService;
import servicios.VentaDetalleService;
import servicios.VentaService;

/**
 *
 * @author javie
 */
public class ventaform extends javax.swing.JFrame {

    /**
     * Creates new form ventaform
     */
    private Connection conection = null;
    private VentaService ventaservice;
    private VentaDetalleService ventadetalleservice;
    private ClienteService clienteservice;
    private TipoDocumentoService tipodocumentoservice;
    private Venta venta = new Venta();
    private Cliente cliente = new Cliente();
    private TipoDocumento tipodocumento = new TipoDocumento();
    private DefaultTableModel modeltableplatos;
    private DefaultComboBoxModel modelCombo;
    private ArrayList<VentaDetalle> listaventadetalle = new ArrayList<VentaDetalle>();
    private ArrayList<TipoDocumento> listatipodocumento = new ArrayList<TipoDocumento>();
    
    public ventaform() {
        initComponents();
        this.setLocationRelativeTo(this);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
        Conexion conexion = new Conexion();
        conection = conexion.iniciarConexion();
        ventaservice = new VentaService(conection);
        ventadetalleservice = new VentaDetalleService(conection);
        clienteservice = new ClienteService(conection);
        tipodocumentoservice = new TipoDocumentoService(conection);
        modeltableplatos = (DefaultTableModel)tablePlatos.getModel();
        modelCombo = (DefaultComboBoxModel)cbTipodocumento.getModel();
        fechaActual();
        listarTiposDocumento();
    }

    public void fechaActual() {
        Calendar c1 = Calendar.getInstance();
        String fechaventa = ((JTextField)jdFecha.getDateEditor().getUiComponent()).getText();
        jdFecha.setCalendar(c1);
        venta.setFecha(fechaventa);
    }
    
    public void listarTiposDocumento() {
        listatipodocumento = tipodocumentoservice.listarTiposdocumento();
        for (int i = 0; i < listatipodocumento.size(); i++) {
            modelCombo.addElement(listatipodocumento.get(i).nombre);
        }
    }
    
    public void listarVentaDetalles(Venta venta) {
        System.out.println("antes de limpjiar");
        limpiarTablaPlato();
        listaventadetalle = ventadetalleservice.listarVentaDetalles(venta);
        for (int i = 0; i < listaventadetalle.size(); i++) {
            System.out.println(listaventadetalle.get(i).nombreplato);
            modeltableplatos.addRow(new Object[]{listaventadetalle.get(i).id, listaventadetalle.get(i).nombreplato,
                listaventadetalle.get(i).cantidad, listaventadetalle.get(i).precio});
        }
    }
    
    public TipoDocumento buscarTipoDocumento(String nombre) {
        TipoDocumento categoria = null;
        int n = -1;
        do {
            n++;
            categoria = listatipodocumento.get(n);
        }
        while(!nombre.equals(listatipodocumento.get(n).nombre));
        return categoria;
    }
    
    public TipoDocumento buscarTipoDocumentoId(int id) {
        TipoDocumento categoria = null;
        int n = -1;
        do {
            n++;
            categoria = listatipodocumento.get(n);
        }
        while(categoria.id != id);
        return categoria;
    }
    
    public void onSelectedTipoDocumento(int id) {
        TipoDocumento categoria = null;
        int n = -1;
        do {
            n++;
            categoria = listatipodocumento.get(n);
            if (categoria.id == id) {
                cbTipodocumento.setSelectedIndex(n);
            }
        }
        while(categoria.id != id);
    }
    
    public void limpiarTablaPlato() {
        try {
            int filas = tablePlatos.getRowCount();
            for (int i = 0;filas > i; i++) {
                modeltableplatos.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }
    
    public void limpiar() {
        venta = new Venta();
        cliente = new Cliente();
        rsmtNumerodocumento.setText("");
        rsmtNumeroPedido.setText("");
        lblFechaPedido.setText("");
        lblNombreCliente.setText("");
        lblMontoTotal.setText("S/ 0");
        listaventadetalle = new ArrayList<VentaDetalle>();
        
        btnPagar.setEnabled(false);
        btnImprimir.setEnabled(false);
        jdFecha.setEnabled(true);
        // cbTipodocumento.setEnabled(true);
        rsmtNumerodocumento.setEditable(true);
        venta = new Venta();
        limpiarTablaPlato();
    }
    
    public void setMostrarReporte() {
        // aca es el codigo del reporte en jasper report
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        rsmtNumeroPedido = new rojeru_san.RSMTextFull();
        btnBuscar = new javax.swing.JButton();
        jdFecha = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblFechaPedido = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblNombreCliente = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePlatos = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lblMontoTotal = new javax.swing.JLabel();
        btnLimpiar = new javax.swing.JButton();
        btnPagar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cbTipodocumento = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        rsmtNumerodocumento = new rojeru_san.RSMTextFull();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Venta");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Búsqueda"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 112, 192));
        jLabel1.setText("Número de pedido *");

        rsmtNumeroPedido.setPlaceholder("Numero de pedido...");

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/img/buscar.png"))); // NOI18N
        btnBuscar.setToolTipText("buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jdFecha.setDateFormatString("yyyy/MM/dd HH:mm:ss");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 112, 192));
        jLabel7.setText("Fecha venta");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(rsmtNumeroPedido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jdFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(btnBuscar)
                                .addGap(12, 12, 12))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(rsmtNumeroPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addGap(22, 22, 22))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jdFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos pedido"));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 112, 192));
        jLabel2.setText("Fecha pedido");

        lblFechaPedido.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 112, 192));
        jLabel4.setText("Cliente");

        lblNombreCliente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(lblFechaPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(lblNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNombreCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFechaPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de platos"));

        tablePlatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "N°", "Producto", "Cantidad", "Precio"
            }
        ));
        jScrollPane1.setViewportView(tablePlatos);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 112, 192));
        jLabel6.setText("Monto Total");

        lblMontoTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMontoTotal.setForeground(new java.awt.Color(0, 112, 192));
        lblMontoTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMontoTotal.setText("S/ 0");
        lblMontoTotal.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/img/limpiar.png"))); // NOI18N
        btnLimpiar.setToolTipText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnPagar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/img/dinero.png"))); // NOI18N
        btnPagar.setToolTipText("Pagar");
        btnPagar.setEnabled(false);
        btnPagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPagarActionPerformed(evt);
            }
        });

        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/img/impresion.png"))); // NOI18N
        btnImprimir.setToolTipText("Imprimir");
        btnImprimir.setEnabled(false);
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(34, 34, 34)
                .addComponent(lblMontoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnImprimir)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblMontoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6))
                    .addComponent(btnLimpiar)
                    .addComponent(btnPagar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Venta"));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 112, 192));
        jLabel3.setText("Tipo documento");

        cbTipodocumento.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbTipodocumentoItemStateChanged(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 112, 192));
        jLabel5.setText("N° documento *");

        rsmtNumerodocumento.setPlaceholder("Numero de pedido...");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(cbTipodocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jLabel5)
                .addGap(32, 32, 32)
                .addComponent(rsmtNumerodocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbTipodocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(rsmtNumerodocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        if (!rsmtNumeroPedido.getText().equals("")) {
            venta = ventaservice.mostrarVenta(Integer.parseInt(rsmtNumeroPedido.getText()));
            System.out.println("venta.numdocumento: "+venta.numdocumento);
            if (venta != null) {
                System.out.println("venta.cliente_id: "+venta.cliente_id);
                cliente = clienteservice.buscarCliente(venta.cliente_id);
                lblFechaPedido.setText(venta.getFecha());
                lblNombreCliente.setText(cliente.getNombres()+" ("+cliente.getDni()+")");
                lblMontoTotal.setText("S/ "+venta.getPreciototal());
                listarVentaDetalles(venta);
                rsmtNumerodocumento.setText("");
                if (venta.numdocumento != null) {
                    btnPagar.setEnabled(false);
                    btnImprimir.setEnabled(true);
                    jdFecha.setEnabled(false);
                    rsmtNumerodocumento.setEditable(false);
                    // cbTipodocumento.setEnabled(false);
                    jdFecha.setDateFormatString(venta.getFechaventa());
                    rsmtNumerodocumento.setText(venta.numdocumento);
                    tipodocumento = buscarTipoDocumentoId(venta.tipodocumento_id);
                    onSelectedTipoDocumento(venta.tipodocumento_id);
                    
                } else {
                    System.out.println("no hay ");
                    btnPagar.setEnabled(true);
                    btnImprimir.setEnabled(false);
                    jdFecha.setEnabled(true);
                    // cbTipodocumento.setEnabled(true);
                    rsmtNumerodocumento.setEditable(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "El número de pedido no existe..", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnPagarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPagarActionPerformed
        // TODO add your handling code here:
        String fechaventa = ((JTextField)jdFecha.getDateEditor().getUiComponent()).getText();
        if (venta.id > 0 && !rsmtNumerodocumento.getText().equals("") && !fechaventa.equals("")) {
            venta.setTipodocumento_id(tipodocumento.id);
            venta.setNumdocumento(rsmtNumerodocumento.getText());
            venta.setFechaventa(fechaventa);
            new CobrarMesa(venta, listaventadetalle, tipodocumento, conection, this).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Busque el pedido e ingrese el numero de documento y fecha venta.", 
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnPagarActionPerformed

    private void cbTipodocumentoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbTipodocumentoItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            //Object item = evt.getItem();
            tipodocumento = buscarTipoDocumento(evt.getItem().toString());
            System.out.println(tipodocumento.nombre);
          // do something with object
       }
    }//GEN-LAST:event_cbTipodocumentoItemStateChanged

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnImprimirActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ventaform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ventaform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ventaform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ventaform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ventaform().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnPagar;
    private javax.swing.JComboBox<String> cbTipodocumento;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdFecha;
    private javax.swing.JLabel lblFechaPedido;
    private javax.swing.JLabel lblMontoTotal;
    private javax.swing.JLabel lblNombreCliente;
    private rojeru_san.RSMTextFull rsmtNumeroPedido;
    private rojeru_san.RSMTextFull rsmtNumerodocumento;
    private javax.swing.JTable tablePlatos;
    // End of variables declaration//GEN-END:variables
}
