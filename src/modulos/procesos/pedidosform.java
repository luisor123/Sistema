/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modulos.procesos;

import entidades.CategoriaPlato;
import entidades.Cliente;
import entidades.GlobalConstants;
import entidades.Plato;
import entidades.Venta;
import entidades.VentaDetalle;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import modulos.consultas.buscarclienteform;
import modulos.sistema.menuprincipal.Conexion;
import servicios.CategoriaPlatoService;
import servicios.PlatoService;
import servicios.VentaDetalleService;
import servicios.VentaService;

/**
 *
 * @author javie
 */
public class pedidosform extends javax.swing.JFrame {

    /**
     * Creates new form pedidosform
     */
    private Connection conection = null;
    private VentaService pedidoervice;
    private VentaDetalleService pedidodetalleservice;
    private CategoriaPlatoService categoriaservice;
    private PlatoService platoservice;
    private CategoriaPlato categoria;
    private DefaultComboBoxModel modelCombo;
    private DefaultTableModel modeltableplatos;
    private DefaultTableModel modeltableplatoscliente;
    private ArrayList<CategoriaPlato> listacategorias = new ArrayList<CategoriaPlato>();
    private ArrayList<Plato> listaplatos = new ArrayList<Plato>();
    private ArrayList<Plato> listaplatoscliente = new ArrayList<Plato>();
    private ArrayList<VentaDetalle> listapedidodetalle = new ArrayList<VentaDetalle>();
    private Cliente cliente = new Cliente();
    private Venta pedido = new Venta();
    private double montoTotal = 0.0;
    private int numpedido = 0;
    
    public pedidosform() {
        initComponents();
        this.setLocationRelativeTo(this);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
        Conexion conexion = new Conexion();
        conection = conexion.iniciarConexion();
        pedidoervice = new VentaService(conection);
        categoriaservice = new CategoriaPlatoService(conection);
        platoservice = new PlatoService(conection);
        pedidodetalleservice = new VentaDetalleService(conection);
        categoria = new CategoriaPlato();
        modelCombo = (DefaultComboBoxModel)cbCategoria.getModel();
        modeltableplatos = (DefaultTableModel)tablePlatos.getModel();
        modeltableplatoscliente = (DefaultTableModel)tablePlatosCliente.getModel();
        lblMontoTotal.setText("S/ "+montoTotal);
        System.out.println("id usuario: "+GlobalConstants.usuario.id);
        System.out.println("nombre suario: "+GlobalConstants.usuario.nombres);
        pedido.setUsuario_id(GlobalConstants.usuario.id);
        fechaActual();
        listenerCambiarCelda();
        listarPlatos();
        listarCategorias();
        generarCodigoPedido();
    }
    
    public void fechaActual() {
        Calendar c1 = Calendar.getInstance();
        String fecha = c1.get(Calendar.DATE)+"-"+c1.get(Calendar.MONTH)+1+"-"+c1.get(Calendar.YEAR);
        rsmtFecha.setText(fecha);
        pedido.setFecha(fecha);
    }
    
    public void listenerCambiarCelda() {
        modeltableplatoscliente.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE && e.getColumn() == 2) {
                    // System.out.println("CAMBIO ("+e.getFirstRow()+".. "+e.getColumn()+"");
                    String cantidad = modeltableplatoscliente.getValueAt(e.getFirstRow(), e.getColumn()).toString();
                    String precio = modeltableplatoscliente.getValueAt(e.getFirstRow(), 3).toString();
                    double preciototal = Integer.parseInt(cantidad)*Double.parseDouble(precio);
                    int plato_id = Integer.parseInt(modeltableplatoscliente.getValueAt(e.getFirstRow(), 0).toString());
                    actualizarCantidadEnPedidoDetalle(plato_id, Integer.parseInt(cantidad), preciototal);
                    modeltableplatoscliente.setValueAt(preciototal, 
                        e.getFirstRow(), 4);
                    calcularCantidadTotal();
                }
            }
        });
    }
    
    public void listarCategorias() {
        listacategorias = categoriaservice.listarCategoriasPlato();
        for (int i = 0; i < listacategorias.size(); i++) {
            modelCombo.addElement(listacategorias.get(i).nombre);
        }
    }
    
    public void listarPlatos() {
        limpiarTablaPlato();
        listaplatos = platoservice.listarPlatos();
        for (int i = 0; i < listaplatos.size(); i++) {
            modeltableplatos.addRow(new Object[]{listaplatos.get(i).id, listaplatos.get(i).nombre,
                listaplatos.get(i).precio});
        }
    }
    
    public void generarCodigoPedido() {
        numpedido = pedidoervice.generarCodigoPedido();
        pedido.setNumpedido(numpedido);
        lblNumeroPedido.setText(numpedido+"");
    }
    
    public void listarPlatosCategoria(CategoriaPlato categoria) {
        limpiarTablaPlato();
        ArrayList<Plato> newLista =  new ArrayList<Plato>();
        for (int i = 0; i < listaplatos.size(); i++) {
            if (listaplatos.get(i).categoria_id == categoria.id) {
                System.out.println(listaplatos.get(i).nombre);
                newLista.add(listaplatos.get(i));
            }
        }
        // agregamos a la tabla
        for (int i = 0; i < newLista.size(); i++) {
            modeltableplatos.addRow(new Object[]{newLista.get(i).id, newLista.get(i).nombre,
                newLista.get(i).precio});
        }
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
    
    public void limpiarTablaPlatoCliente() {
        try {
            int filas = tablePlatosCliente.getRowCount();
            for (int i = 0;filas > i; i++) {
                modeltableplatoscliente.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar la tabla.");
        }
    }

    public CategoriaPlato buscarCategoria(String nombre) {
        CategoriaPlato categoria = null;
        int n = -1;
        do {
            n++;
            categoria = listacategorias.get(n);
        }
        while(!nombre.equals(listacategorias.get(n).nombre));
        return categoria;
    }
    
    public Plato buscarPlato(int id) {
        Plato plato = null;
        int n = -1;
        do {
            n++;
            plato = listaplatos.get(n);
        }
        while(plato.id != id);
        return plato;
    }
    
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
        rsmtNombre.setText(this.cliente.nombres+" ("+cliente.dni+")");
        pedido.setCliente_id(cliente.id);
    }
    
    public void calcularCantidadTotal() {
        double preciototal = 0, precio = 0;
        for (int i = 0; i < tablePlatosCliente.getRowCount(); i++) {
            precio = Integer.parseInt(tablePlatosCliente.getValueAt(i, 2).toString()) * 
                    Double.parseDouble(tablePlatosCliente.getValueAt(i, 3).toString());
            preciototal = preciototal + precio;
        }
        montoTotal = preciototal;
        pedido.setPreciototal(preciototal);
        lblMontoTotal.setText("S/"+montoTotal);
    }
    
    public void limpiar() {
        rsmtNombre.setText("");
        cliente = new Cliente();
        pedido = new Venta();
        listaplatoscliente = new ArrayList<Plato>();
        limpiarTablaPlatoCliente();
        montoTotal = 0.0;
        lblMontoTotal.setText("S/ "+montoTotal);
        
    }
    
    public void eliminarPlatoCliente(int id) {
        Plato plato = null;
        int n = -1;
        do {
            n++;
            plato = listaplatoscliente.get(n);
            if (plato.id == id) {
                listaplatoscliente.remove(n);
            }
        }
        while(plato.id != id);
    }
    
    public void eliminarPedidoDetalle(int plato_id) {
        VentaDetalle pedido = null;
        int n = -1;
        do {
            n++;
            pedido = listapedidodetalle.get(n);
            if (pedido.id == plato_id) {
                listaplatoscliente.remove(n);
            }
        }
        while(pedido.id != plato_id);
    }
    
    public void actualizarCantidadEnPedidoDetalle(int plato_id, int cantidad, double precio) {
        VentaDetalle pedido = null;
        int n = -1;
        do {
            n++;
            pedido = listapedidodetalle.get(n);
            if (pedido.plato_id == plato_id) {
                listapedidodetalle.get(n).setCantidad(cantidad);
                listapedidodetalle.get(n).setPrecio(precio);
            }
        }
        while(pedido.plato_id != plato_id);
    }
    
    public boolean existePlatoCliente(int id) {
        System.out.println("id: "+id);
        boolean existe = false;
        Plato plato = null;
        for (int i = 0; i < listaplatoscliente.size(); i++) {
            plato = listaplatoscliente.get(i);
            if (plato.id == id) {
                existe = true;
            }
        }
        return existe;
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
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePlatos = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbCategoria = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        rsmtFecha = new rojeru_san.RSMTextFull();
        jLabel4 = new javax.swing.JLabel();
        lblNumeroPedido = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        rsmtNombre = new rojeru_san.RSMTextFull();
        btnBuscar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablePlatosCliente = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        btnLimpiar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblMontoTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Platos"));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de comidas"));

        tablePlatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Precio"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePlatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePlatosMouseClicked(evt);
            }
        });
        tablePlatos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablePlatosKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tablePlatos);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Búsqueda"));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 112, 192));
        jLabel1.setText("Categoría");

        cbCategoria.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbCategoriaItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(64, 64, 64))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 112, 192));
        jLabel2.setText("Fecha");

        rsmtFecha.setEditable(false);
        rsmtFecha.setBordeColorNoFocus(new java.awt.Color(153, 153, 153));
        rsmtFecha.setFont(new java.awt.Font("Roboto Bold", 1, 12)); // NOI18N
        rsmtFecha.setModoMaterial(true);
        rsmtFecha.setPlaceholder("Ingrese nombre...");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 112, 192));
        jLabel4.setText("N° pedido");

        lblNumeroPedido.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblNumeroPedido.setForeground(new java.awt.Color(0, 112, 192));
        lblNumeroPedido.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNumeroPedido.setText("012");
        lblNumeroPedido.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(rsmtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(lblNumeroPedido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rsmtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(lblNumeroPedido, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        rsmtNombre.setEditable(false);
        rsmtNombre.setBordeColorNoFocus(new java.awt.Color(153, 153, 153));
        rsmtNombre.setFont(new java.awt.Font("Roboto Bold", 1, 12)); // NOI18N
        rsmtNombre.setModoMaterial(true);
        rsmtNombre.setPlaceholder("Ingrese nombre...");

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/img/buscar.png"))); // NOI18N
        btnBuscar.setToolTipText("buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rsmtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscar)
                    .addComponent(rsmtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado comidas: cliente"));

        tablePlatosCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Codigo", "Nombre", "Cantidad", "Precio", "Precio Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablePlatosCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablePlatosClienteMousePressed(evt);
            }
        });
        tablePlatosCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tablePlatosClienteKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tablePlatosCliente);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/img/limpiar.png"))); // NOI18N
        btnLimpiar.setToolTipText("Nuevo");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/img/cerrar.png"))); // NOI18N
        btnEliminar.setToolTipText("Eliminar");
        btnEliminar.setEnabled(false);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/img/guardar.png"))); // NOI18N
        btnGuardar.setToolTipText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 112, 192));
        jLabel3.setText("Monto Total");

        lblMontoTotal.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblMontoTotal.setForeground(new java.awt.Color(0, 112, 192));
        lblMontoTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMontoTotal.setText("012");
        lblMontoTotal.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(43, 43, 43)
                .addComponent(lblMontoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGuardar)
                    .addComponent(btnLimpiar)
                    .addComponent(btnEliminar))
                .addContainerGap())
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblMontoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tablePlatosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablePlatosKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tablePlatosKeyReleased

    private void tablePlatosClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tablePlatosClienteKeyReleased
        // TODO add your handling code here:
        btnEliminar.setEnabled(true);
    }//GEN-LAST:event_tablePlatosClienteKeyReleased

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        int fila = tablePlatosCliente.getSelectedRow();
        System.out.println("fila seleccinodad: "+fila);
        System.out.println("nombre: "+tablePlatosCliente.getValueAt(fila, 1).toString());
        System.out.println("id: "+tablePlatosCliente.getValueAt(fila, 0).toString());
        int plato_id = Integer.parseInt(tablePlatosCliente.getValueAt(fila, 0).toString());
        eliminarPlatoCliente(plato_id);
        eliminarPedidoDetalle(plato_id);
        modeltableplatoscliente.removeRow(fila);
        if (tablePlatosCliente.getRowCount() == 0) {
            btnEliminar.setEnabled(false);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if (!rsmtNombre.getText().equals("") && listaplatoscliente.size() != 0) {
            // ingresar el pedido
            int grabar = JOptionPane.showConfirmDialog(this, "¿Deseas grabar el pedido?", 
                    "Registrar", JOptionPane.YES_NO_CANCEL_OPTION);
            if (grabar == 0) {
                boolean rptaventa = pedidoervice.ingresarPedido(pedido);
                pedido = pedidoervice.mostrarVenta(pedido.numpedido);
                if (rptaventa) {
                    boolean rptafinal = true, rptapedetalle = false;
                    for (int i = 0; i < listapedidodetalle.size(); i++) {
                        listapedidodetalle.get(i).setVenta_id(pedido.id);
                        rptapedetalle = pedidodetalleservice.ingresarVentaDetalle(listapedidodetalle.get(i));
                        if (!rptapedetalle){
                            rptafinal = rptapedetalle;
                        }
                    }
                    if (rptafinal) {
                        limpiar();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al ingresar el pedido detalle.", "Error", 
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Error al ingresar el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar el cliente y asignar al menos un plato.", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void cbCategoriaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbCategoriaItemStateChanged
        // TODO add your handling code here:
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            //Object item = evt.getItem();
            categoria = buscarCategoria(evt.getItem().toString());
            System.out.println(categoria.id);
            listarPlatosCategoria(categoria);
          // do something with object
       }
    }//GEN-LAST:event_cbCategoriaItemStateChanged

    private void tablePlatosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePlatosMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            int fila = tablePlatos.getSelectedRow();
            String cantidad = JOptionPane.showInputDialog(null, "Ingrese cantidad:", 
                    tablePlatos.getValueAt(fila, 1).toString(), JOptionPane.QUESTION_MESSAGE);
            
            if (!cantidad.equals("")) {
                if (!existePlatoCliente(Integer.parseInt(tablePlatos.getValueAt(fila, 0).toString()))) {
                    Plato plato = buscarPlato(Integer.parseInt(tablePlatos.getValueAt(fila, 0).toString()));
                    modeltableplatoscliente.addRow(new Object[]{plato.id, plato.nombre, cantidad, plato.precio, 
                        Integer.parseInt(cantidad)*plato.precio});
                    listaplatoscliente.add(plato);
                    // ingreso pedidodetalle
                    VentaDetalle pedidodetalle = new VentaDetalle();
                    pedidodetalle.setPlato_id(plato.id);
                    pedidodetalle.setNombreplato(plato.nombre);
                    pedidodetalle.setPrecioplato(plato.precio);
                    pedidodetalle.setCantidad(Integer.parseInt(cantidad));
                    pedidodetalle.setPrecio(Integer.parseInt(cantidad) * plato.precio);
                    listapedidodetalle.add(pedidodetalle);
                    //
                    montoTotal = montoTotal + Integer.parseInt(cantidad)*plato.precio;
                    pedido.setPreciototal(montoTotal);
                    lblMontoTotal.setText("S/ "+montoTotal);
                } else {
                    JOptionPane.showMessageDialog(null, "El plato ya ha sido asignado", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                }
            }
         }
    }//GEN-LAST:event_tablePlatosMouseClicked

    private void tablePlatosClienteMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablePlatosClienteMousePressed
        // TODO add your handling code here:
        btnEliminar.setEnabled(true);
    }//GEN-LAST:event_tablePlatosClienteMousePressed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        new buscarclienteform(this).setVisible(true);
    }//GEN-LAST:event_btnBuscarActionPerformed

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
            java.util.logging.Logger.getLogger(pedidosform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pedidosform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pedidosform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pedidosform.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pedidosform().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cbCategoria;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblMontoTotal;
    private javax.swing.JLabel lblNumeroPedido;
    private rojeru_san.RSMTextFull rsmtFecha;
    private rojeru_san.RSMTextFull rsmtNombre;
    private javax.swing.JTable tablePlatos;
    private javax.swing.JTable tablePlatosCliente;
    // End of variables declaration//GEN-END:variables
}
